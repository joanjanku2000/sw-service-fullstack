package org.swisscom.serviceapp.infrastructure.dto;

import java.io.Serializable;

/**
 * Object used only as D-T-O
 */
public record OwnerDto(String id, String name, String accountNumber, Integer level)
        implements Serializable {

}
