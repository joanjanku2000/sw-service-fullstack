package org.swisscom.service_app.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResourceDTO {
    private String id;
    private List<OwnerDTO> owners;
}
