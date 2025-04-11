package org.swisscom.serviceapp.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.swisscom.serviceapp.containers.ContainerBase;
import org.swisscom.serviceapp.model.dto.AppServiceDTO;
import org.swisscom.serviceapp.model.dto.OwnerDTO;
import org.swisscom.serviceapp.model.dto.ResourceDTO;

import java.util.List;

class AppServiceServiceTest extends ContainerBase {

    @Autowired
    private AppServiceService appServiceService;

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

    @Test
    @DisplayName("Test 1 - Save PASS")
    void testSave() {
        AppServiceDTO appServiceDTO = generateAppServiceDTO();

        AppServiceDTO appServiceResult = appServiceService.save(appServiceDTO);

        Assertions.assertEquals(appServiceResult.getId(), appServiceService.findById(appServiceResult.getId()).getId());
    }
}
