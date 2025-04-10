package org.swisscom.service_app.api.dto;

import lombok.Data;

@Data
public class OwnerDTO {
    private String id;
    private String name;
    private String accountNumber;
    private Integer level;
}
