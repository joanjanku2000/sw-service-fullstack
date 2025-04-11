package org.swisscom.serviceapp.infrastructure.api.exception;

public enum ExceptionMessage {
    NOT_FOUND("%s with id %s not found");

    final String message;

    ExceptionMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
