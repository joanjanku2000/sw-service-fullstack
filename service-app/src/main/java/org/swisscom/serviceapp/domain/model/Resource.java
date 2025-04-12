package org.swisscom.serviceapp.domain.model;

import java.util.List;
import java.util.UUID;

/**
 * Object stored directly into mongodb
 */
public class Resource {
    private UUID id;
    private List<Owner> owners;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Owner> getOwners() {
        return owners;
    }

    public void setOwners(List<Owner> owners) {
        this.owners = owners;
    }

}
