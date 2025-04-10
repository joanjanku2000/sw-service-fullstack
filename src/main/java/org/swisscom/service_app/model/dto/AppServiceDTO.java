package org.swisscom.service_app.model.dto;

import java.util.List;

/**
 * Object used only as D-T-O
 */
public class AppServiceDTO {
    private String id;
    private List<ResourceDTO> resources;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ResourceDTO> getResources() {
        return resources;
    }

    public void setResources(List<ResourceDTO> resources) {
        this.resources = resources;
    }
}
