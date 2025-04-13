package org.swisscom.serviceapp.infrastructure.api.exception;

/**
 * Custom exception for resource not found occasions
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
