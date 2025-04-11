package org.swisscom.serviceapp.mapper;

import org.swisscom.serviceapp.model.AppService;
import org.swisscom.serviceapp.model.dto.AppServiceDTO;

import java.util.UUID;
import java.util.stream.Collectors;

public class ServiceMapper {

    private ServiceMapper() {
        // not-instantiable
    }

    public static AppServiceDTO toDTO(AppService appService) {
        AppServiceDTO appServiceDTO = new AppServiceDTO();

        appServiceDTO.setId(appService.getId());
        appServiceDTO.setResources(appService.getResources().stream().map(ResourceMapper::toDTO).toList());

        return appServiceDTO;
    }

    public static AppService toEntity(AppServiceDTO appServiceDTO) {
        AppService appService = new AppService();

        appService.setId(UUID.randomUUID());

        appService.setResources(appServiceDTO.getResources().stream().map(ResourceMapper::toEntity).collect(Collectors.toList()));

        return appService;
    }

    public static AppService toEntityForUpdate(AppService service, AppServiceDTO appServiceDTO) {

        service.setResources(appServiceDTO.getResources().stream().map(ResourceMapper::toEntity).collect(Collectors.toList()));

        return service;
    }
}
