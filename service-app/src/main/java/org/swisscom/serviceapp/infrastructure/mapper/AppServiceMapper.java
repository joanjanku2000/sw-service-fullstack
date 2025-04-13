package org.swisscom.serviceapp.infrastructure.mapper;

import org.swisscom.serviceapp.domain.model.AppService;
import org.swisscom.serviceapp.infrastructure.dto.AppServiceDto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Mapper Class for AppService to convert
 * between object instances which are persisted and DTOs
 */
public class AppServiceMapper {

    private AppServiceMapper() {
        // not-instantiable
    }

    public static AppServiceDto toDTO(AppService appService) {
        return new AppServiceDto(appService.getId(), ResourceMapper.toDtoList(appService.getResources()), appService.getVersion(), appService.getCreatedDateTime());
    }

    public static AppService toEntity(AppServiceDto appServiceDTO) {
        AppService appService = new AppService();

        appService.setId(UUID.randomUUID());
        appService.setCreatedDateTime(LocalDateTime.now());
        appService.setResources(ResourceMapper.toEntityList(appServiceDTO.resources()));

        return appService;
    }

    public static AppService toEntityForUpdate(AppService appService, AppServiceDto appServiceDto) {
        appService.setResources(ResourceMapper.toEntityListForUpdate(appServiceDto.resources()));
        return appService;
    }

}
