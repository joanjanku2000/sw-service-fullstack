package org.swisscom.serviceapp.infrastructure.mapper;

import org.swisscom.serviceapp.domain.model.Resource;
import org.swisscom.serviceapp.infrastructure.dto.ResourceDto;

import java.util.List;
import java.util.UUID;

public class ResourceMapper {

    private ResourceMapper() {
        // not-instantiable
    }

    public static ResourceDto toDTO(final Resource resource) {
        return new ResourceDto(resource.getId().toString(), OwnerMapper.toDtoList(resource.getOwners()));
    }

    public static Resource toEntity(final ResourceDto resourceDTO) {
        Resource resource = new Resource();

        resource.setId(UUID.randomUUID());
        resource.setOwners(OwnerMapper.toEntityList(resourceDTO.owners()));

        return resource;
    }

    public static Resource toEntityForUpdate(final ResourceDto resourceDto) {
        Resource resource = new Resource();

        resource.setId(resourceDto.id() != null ? UUID.fromString(resourceDto.id()) : UUID.randomUUID());

        resource.setOwners(OwnerMapper.toEntityListForUpdate(resourceDto.owners()));

        return resource;
    }

    public static List<Resource> toEntityListForUpdate(final List<ResourceDto> resourceDtoList) {
        return resourceDtoList.stream().map(ResourceMapper::toEntityForUpdate).toList();
    }

    public static List<Resource> toEntityList(final List<ResourceDto> resourceDtoList) {
        return resourceDtoList.stream().map(ResourceMapper::toEntity).toList();
    }

    public static List<ResourceDto> toDtoList(final List<Resource> resourceList) {
        return resourceList.stream().map(ResourceMapper::toDTO).toList();
    }

    public static List<Resource> toUpdatedEntityList(final List<ResourceDto> resourceDtoList) {
      return resourceDtoList.stream().map(ResourceMapper::toEntity).toList();
    }

}
