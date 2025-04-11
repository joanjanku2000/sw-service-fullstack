package org.swisscom.serviceapp.infrastructure.mapper;

import org.swisscom.serviceapp.domain.model.AppService;
import org.swisscom.serviceapp.infrastructure.dto.AppServiceDto;

import java.util.UUID;

public class ServiceMapper {

    private ServiceMapper() {
        // not-instantiable
    }

    public static AppServiceDto toDTO(AppService appService) {
        return new AppServiceDto(appService.getId(), appService.getResources().stream().map(ResourceMapper::toDTO).toList());
    }

    public static AppService toEntity(AppServiceDto appServiceDTO) {
        AppService appService = new AppService();

        appService.setId(UUID.randomUUID());

        appService.setResources(appServiceDTO.resources().stream().map(ResourceMapper::toEntity).toList());

        return appService;
    }

    public static AppService toEntityForUpdate(AppService service, AppServiceDto appServiceDTO) {

        service.setResources(appServiceDTO.resources().stream().map(ResourceMapper::toEntity).toList());

        return service;
    }
}
