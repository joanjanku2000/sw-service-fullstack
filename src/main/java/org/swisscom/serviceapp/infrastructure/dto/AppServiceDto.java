package org.swisscom.serviceapp.infrastructure.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * Object used only as D-T-O
 */
public record AppServiceDto(UUID id,@Valid @NotNull(message = "Resources cannot be null") List<ResourceDto> resources) {

}
