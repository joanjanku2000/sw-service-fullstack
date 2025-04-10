package org.swisscom.service_app.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ServiceDTO {
    private String id;
    private List<ResourceDTO> resources;
}
