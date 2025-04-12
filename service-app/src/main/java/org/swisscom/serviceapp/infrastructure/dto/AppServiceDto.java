package org.swisscom.serviceapp.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Object used only as D-T-O
 */
public record AppServiceDto(UUID id,
                            @Valid @NotNull(message = "Resources cannot be null") @NotEmpty(message = "Resources cannot be empty") List<ResourceDto> resources,
                            Integer version,
                            @JsonSerialize(using = LocalDateTimeSerializer.class) @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime createdDateTime)
        implements Serializable {

}
