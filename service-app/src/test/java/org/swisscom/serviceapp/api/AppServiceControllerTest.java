package org.swisscom.serviceapp.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.swisscom.serviceapp.ServiceAppApplication;
import org.swisscom.serviceapp.containers.ContainerBase;
import org.swisscom.serviceapp.domain.model.AppService;
import org.swisscom.serviceapp.infrastructure.api.exception.ExceptionMessage;
import org.swisscom.serviceapp.infrastructure.dto.AppServiceDto;
import org.swisscom.serviceapp.infrastructure.dto.OwnerDto;
import org.swisscom.serviceapp.infrastructure.dto.ResourceDto;
import org.swisscom.serviceapp.infrastructure.mapper.AppServiceMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceAppApplication.class})
@WebAppConfiguration
class AppServiceControllerTest extends ContainerBase {
    private static final int DEFAULT_VERSION = 1;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
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
        AppServiceDto appServiceDTO = generateAppServiceDto();

        this.mockMvc.perform(post(Endpoints.SAVE.getUri())
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJson(appServiceDTO)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.resources[0].owners[0].name")
                        .value("Joan"));
    }

    @Test
    void givenMissingResources_thenSaveNotSuccessful() throws Exception {
        AppServiceDto appServiceDTO = generateAppServiceDto(null,null);

        this.mockMvc.perform(post(Endpoints.SAVE.getUri())
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJson(appServiceDTO)))
                .andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    void givenMissingOwner_thenSaveNotSuccessful() throws Exception {
        AppServiceDto appServiceDTO = generateAppServiceDto(null,
                List.of(generateResourceDto(null)));

        this.mockMvc.perform(post(Endpoints.SAVE.getUri())
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJson(appServiceDTO)))
                .andDo(print()).andExpect(status().is4xxClientError());
    }

    @Test
    void givenExistingService_thenUpdateSuccessful() throws Exception {
        String updatedName = "JOAN_CHANGED";
        AppServiceDto appServiceDTO = generateAppServiceDto();

        MvcResult mvcResult = this.mockMvc.perform(post(Endpoints.SAVE.getUri())
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJson(appServiceDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resources[0].owners[0].name")
                        .value("Joan")).andReturn();

        AppService appService = objectMapper.readValue(mvcResult.getResponse().getContentAsString()
                ,AppService.class);

        appService.getResources().get(0).getOwners().get(0).setName(updatedName);

        AppServiceDto toUseForUpdate = AppServiceMapper.toDTO(appService);

        this.mockMvc.perform(put(Endpoints.UPDATE.getUri(), appService.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJson(toUseForUpdate)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.resources[0].owners[0].name")
                        .value(updatedName));
    }

    @Test
    void givenNotMatchingVersion_thenUpdateFails() throws Exception {
        String updatedName = "JOAN_CHANGED";
        AppServiceDto appServiceDTO = generateAppServiceDto();

        MvcResult mvcResult = this.mockMvc.perform(post(Endpoints.SAVE.getUri())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getJson(appServiceDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resources[0].owners[0].name")
                        .value("Joan")).andReturn();

        AppService appService = objectMapper.readValue(mvcResult.getResponse().getContentAsString()
                ,AppService.class);

        appService.getResources().get(0).getOwners().get(0).setName(updatedName);
        appService.setVersion(appService.getVersion() - 1); // assuming incremented from another thread

        AppServiceDto toUseForUpdate = AppServiceMapper.toDTO(appService);

        this.mockMvc.perform(put(Endpoints.UPDATE.getUri(), appService.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getJson(toUseForUpdate)))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value(ExceptionMessage.CONCURRENT_MODIFICATION.getMessage()));
    }

    /**
     * Shoots 2 identical calls one inside another thread
     * and the othe as part of the original thread
     */
    @Test
    void givenConcurrentModification_thenOneUpdateFails() throws Exception {
        String updatedName = "JOAN_CHANGED";
        AppServiceDto appServiceDTO = generateAppServiceDto();

        MvcResult mvcResult = this.mockMvc.perform(post(Endpoints.SAVE.getUri())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getJson(appServiceDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resources[0].owners[0].name")
                        .value("Joan")).andReturn();

        AppService appService = objectMapper.readValue(mvcResult.getResponse().getContentAsString()
                ,AppService.class);

        appService.getResources().get(0).getOwners().get(0).setName(updatedName);

        AppServiceDto toUseForUpdate = AppServiceMapper.toDTO(appService);

        Future<ResultActions> resultOfFirstCall = executor.submit(
                () -> this.mockMvc.perform(put(Endpoints.UPDATE.getUri(), appService.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getJson(toUseForUpdate)))
                        .andDo(print())

        );

        Future<ResultActions> resultOfSecondCall = executor.submit(
                () -> this.mockMvc.perform(put(Endpoints.UPDATE.getUri(), appService.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(getJson(toUseForUpdate)))
                        .andDo(print()).andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message")
                                .value(ExceptionMessage.CONCURRENT_MODIFICATION.getMessage()))
        );

        // the order of executions of the above 2 tasks depends on the free threads at the time of execution
        // so it cannot be guaranteed
        // what can and MUST be guaranteed is that since the 2 requests are issued at the same time
        // with the same version , optimistic locking must kick in and 1 of the requests should fail
        Assertions.assertTrue(resultOfSecondCall.get().andReturn().getResponse().getStatus() == HttpStatus.BAD_REQUEST.value()
        || resultOfFirstCall.get().andReturn().getResponse().getStatus() == HttpStatus.BAD_REQUEST.value());

    }

    @Test
    void givenNotExistingService_thenUpdateNotSuccessful() throws Exception {
        AppServiceDto appServiceDTO = generateAppServiceDto();

        this.mockMvc.perform(put(Endpoints.UPDATE.getUri(), UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(getJson(appServiceDTO))).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void givenExistingService_thenItWillBeFound() throws Exception {
        AppServiceDto appServiceDTO = generateAppServiceDto();

        MvcResult mvcResult = this.mockMvc.perform(post(Endpoints.SAVE.getUri())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getJson(appServiceDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resources[0].owners[0].name")
                        .value("Joan")).andReturn();

        AppService appService = objectMapper.readValue(mvcResult.getResponse().getContentAsString()
                ,AppService.class);

        this.mockMvc.perform(get(Endpoints.FIND_BY_ID.getUri(), appService.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.resources[0].owners[0].name")
                        .value("Joan"));
    }

    @Test
    void givenNotExistingService_thenItWillNotBeFound() throws Exception {
        this.mockMvc.perform(get(Endpoints.FIND_BY_ID.getUri(), UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isNotFound());
    }

    <T> String getJson(T obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    static AppServiceDto generateAppServiceDto() {
        OwnerDto ownerDTO = new OwnerDto(null,"Joan", "123435634",8);

        ResourceDto resourceDTO = new ResourceDto(null, List.of(ownerDTO));

        return new AppServiceDto(null, List.of(resourceDTO),DEFAULT_VERSION, LocalDateTime.now());
    }

    static AppServiceDto generateAppServiceDto(UUID id, List<ResourceDto> resourceDtoList) {
        return new AppServiceDto(id, resourceDtoList, DEFAULT_VERSION, LocalDateTime.now());
    }

    static ResourceDto generateResourceDto(List<OwnerDto> ownerDtoList) {
        return new ResourceDto(null,ownerDtoList);
    }
}

