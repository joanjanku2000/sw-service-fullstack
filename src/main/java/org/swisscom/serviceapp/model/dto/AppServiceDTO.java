package org.swisscom.serviceapp.model.dto;

import java.util.List;
import java.util.UUID;

/**
 * Object used only as D-T-O
 */
public class AppServiceDTO {
    private UUID id;
    private List<ResourceDTO> resources;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<ResourceDTO> getResources() {
        return resources;
    }

    public void setResources(List<ResourceDTO> resources) {
        this.resources = resources;
    }
}
