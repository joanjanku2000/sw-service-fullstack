package org.swisscom.serviceapp.model.dto;

import java.util.List;

/**
 * Object used only as D-T-O
 */
public class ResourceDTO {
    private String id;
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
