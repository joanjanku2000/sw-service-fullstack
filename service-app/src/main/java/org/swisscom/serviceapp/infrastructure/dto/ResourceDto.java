package org.swisscom.serviceapp.infrastructure.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;

/**
 * Object used only as D-T-O
 */
public record ResourceDto(String id, @NotNull(message = "Owners cannot be null") @NotEmpty(message = "Owners cannot be empty") List<OwnerDto> owners)
        implements Serializable {

}
