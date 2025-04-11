package org.swisscom.serviceapp.mapper;

import org.swisscom.serviceapp.model.Resource;
import org.swisscom.serviceapp.model.dto.ResourceDTO;

import java.util.UUID;

public class ResourceMapper {

    private ResourceMapper() {
        // not-instantiable
    }

    public static ResourceDTO toDTO(final Resource resource) {
        ResourceDTO resourceDTO = new ResourceDTO();

        resourceDTO.setId(resource.getId().toString());
        resourceDTO.setOwners(resource.getOwners().stream().map(OwnerMapper::toDTO).toList());

        return resourceDTO;
    }

    public static Resource toEntity(final ResourceDTO resourceDTO) {
        Resource resource = new Resource();

        resource.setId(UUID.randomUUID());

        resource.setOwners(resourceDTO.getOwners().stream().map(OwnerMapper::toEntity).toList());

        return resource;
    }

//    public static List<Resource> toListEntity(final List<ResourceDTO> resourceDTO) {
//        Resource resource = new Resource();
//
//        resource.setId(UUID.randomUUID());
//
//        resource.setOwners(resourceDTO.getOwners().stream().map(OwnerMapper::toEntity).toList());
//
//        return resource;
//    }

}
