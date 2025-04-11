package org.swisscom.serviceapp.infrastructure.mapper;

import org.swisscom.serviceapp.domain.model.AppService;
import org.swisscom.serviceapp.infrastructure.dto.AppServiceDto;

import java.util.UUID;

public class ServiceMapper {

    private ServiceMapper() {
        // not-instantiable
    }

    public static AppServiceDto toDTO(AppService appService) {
        return new AppServiceDto(appService.getId(), ResourceMapper.toDtoList(appService.getResources()));
    }

    public static AppService toEntity(AppServiceDto appServiceDTO) {
        AppService appService = new AppService();

        appService.setId(UUID.randomUUID());

        appService.setResources(ResourceMapper.toEntityList(appServiceDTO.resources()));

        return appService;
    }

    public static AppService toEntityForUpdate(AppService service, AppServiceDto appServiceDTO) {

        service.setResources(ResourceMapper.toEntityList(appServiceDTO.resources()));

        return service;
    }
}
