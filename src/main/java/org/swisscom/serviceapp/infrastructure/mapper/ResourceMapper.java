package org.swisscom.serviceapp.infrastructure.mapper;

import org.swisscom.serviceapp.domain.model.Resource;
import org.swisscom.serviceapp.infrastructure.dto.ResourceDto;

import java.util.UUID;

public class ResourceMapper {

    private ResourceMapper() {
        // not-instantiable
    }

    public static ResourceDto toDTO(final Resource resource) {
        return new ResourceDto(resource.getId().toString(),
                resource.getOwners().stream().map(OwnerMapper::toDTO).toList());
    }

    public static Resource toEntity(final org.swisscom.serviceapp.infrastructure.dto.ResourceDto resourceDTO) {
        Resource resource = new Resource();

        resource.setId(UUID.randomUUID());

        resource.setOwners(resourceDTO.owners().stream().map(OwnerMapper::toEntity).toList());

        return resource;
    }


}
