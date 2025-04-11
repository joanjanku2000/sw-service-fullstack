package org.swisscom.serviceapp.infrastructure.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Object used only as D-T-O
 */
public class ResourceDTO {
    private String id;
    @NotNull(message = "Owners cannot be null")
    private List<OwnerDTO> owners;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<OwnerDTO> getOwners() {
        return owners;
    }

    public void setOwners(List<OwnerDTO> owners) {
        this.owners = owners;
    }
}
