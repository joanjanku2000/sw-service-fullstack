package org.swisscom.serviceapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document(collection = "services")
public class AppService {
    @Id
    private UUID id;
    private List<Resource> resources;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    @Override
    public String toString() {
        return "AppService{" +
                "id=" + id +
                ", resources=" + resources +
                '}';
    }
}
