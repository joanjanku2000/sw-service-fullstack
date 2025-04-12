package org.swisscom.serviceapp.infrastructure.dto;

/**
 * Record to help with pagination of services
 * @param offset
 * @param pageSize
 */
public record PageInput(Integer offset,Integer pageSize) {

}
