package org.swisscom.serviceapp.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.swisscom.serviceapp.ServiceAppApplication;
import org.swisscom.serviceapp.containers.ContainerBase;
import org.swisscom.serviceapp.domain.model.AppService;
import org.swisscom.serviceapp.infrastructure.dto.AppServiceDTO;
import org.swisscom.serviceapp.infrastructure.dto.OwnerDTO;
import org.swisscom.serviceapp.infrastructure.dto.ResourceDTO;

import java.util.List;
import java.util.UUID;

import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceAppApplication.class})
@WebAppConfiguration
class AppServiceControllerTest extends ContainerBase {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void givenWac_whenServletContext_thenItProvidesGreetController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        Assertions.assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("appServiceController"));
    }

    @Test
    void givenCorrectPayload_thenSaveSuccessful() throws Exception {
        AppServiceDTO appServiceDTO = generateAppServiceDTO();

        this.mockMvc.perform(post(Endpoints.SAVE.getUri())
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJson(appServiceDTO)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.resources[0].owners[0].name")
                        .value("Joan"));
    }

    @Test
    void givenMissingResources_thenSaveNotSuccessful() throws Exception {
        AppServiceDTO appServiceDTO = generateAppServiceDTO();
        appServiceDTO.setResources(null);

        this.mockMvc.perform(post(Endpoints.SAVE.getUri())
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJson(appServiceDTO)))
                .andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    void givenMissingOwner_thenSaveNotSuccessful() throws Exception {
        AppServiceDTO appServiceDTO = generateAppServiceDTO();
        appServiceDTO.getResources().get(0).setOwners(null);

        this.mockMvc.perform(post(Endpoints.SAVE.getUri())
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJson(appServiceDTO)))
                .andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    void givenExistingService_thenUpdateSuccessful() throws Exception {
        String updatedName = "JOAN_CHANGED";
        AppServiceDTO appServiceDTO = generateAppServiceDTO();

        MvcResult mvcResult = this.mockMvc.perform(post(Endpoints.SAVE.getUri())
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJson(appServiceDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resources[0].owners[0].name")
                        .value("Joan")).andReturn();

        AppService appService = objectMapper.readValue(mvcResult.getResponse().getContentAsString()
                ,AppService.class);

        appServiceDTO.getResources().get(0).getOwners().get(0).setName(updatedName);

        this.mockMvc.perform(put(Endpoints.UPDATE.getUri(), appService.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJson(appServiceDTO)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.resources[0].owners[0].name")
                        .value(updatedName));
    }

    @Test
    void givenNotExistingService_thenUpdateSuccessful() throws Exception {
        AppServiceDTO appServiceDTO = generateAppServiceDTO();


        this.mockMvc.perform(put(Endpoints.UPDATE.getUri(), UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJson(appServiceDTO))).andDo(print())
                .andExpect(status().isNotFound());
    }

    <T> String getJson(T obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    static AppServiceDTO generateAppServiceDTO() {
        OwnerDTO ownerDTO = new OwnerDTO();
        ResourceDTO resourceDTO = new ResourceDTO();
        AppServiceDTO appServiceDTO = new AppServiceDTO();

        ownerDTO.setName("Joan");
        ownerDTO.setAccountNumber("12425675454");
        ownerDTO.setLevel(8);

        resourceDTO.setOwners(List.of(ownerDTO));
        appServiceDTO.setResources(List.of(resourceDTO));

        return appServiceDTO;
    }
}

