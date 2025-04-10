package org.swisscom.service_app.mapper;

import org.swisscom.service_app.model.Resource;
import org.swisscom.service_app.model.dto.ResourceDTO;

import java.util.UUID;

public class ResourceMapper {

    private ResourceMapper() {
        // not-instantiable
    }

    public static ResourceDTO toDTO(Resource resource) {
        ResourceDTO resourceDTO = new ResourceDTO();

        resourceDTO.setId(resource.getId().toString());
        resourceDTO.setOwners(resource.getOwners().stream().map(OwnerMapper::toDTO).toList());

        return resourceDTO;
    }

    public static Resource toEntity(ResourceDTO resourceDTO) {
        Resource resource = new Resource();

        resource.setId(UUID.randomUUID());

        resource.setOwners(resourceDTO.getOwners().stream().map(OwnerMapper::toEntity).toList());

        return resource;
    }

}
