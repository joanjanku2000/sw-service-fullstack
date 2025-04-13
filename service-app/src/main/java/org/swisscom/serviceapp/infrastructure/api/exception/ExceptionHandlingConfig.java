package org.swisscom.serviceapp.infrastructure.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ConcurrentModificationException;

@RestControllerAdvice
public class ExceptionHandlingConfig {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorFormat handleMethodArgumentNotValidExceptions(MethodArgumentNotValidException ex) {
        return new ErrorFormat(HttpStatus.BAD_REQUEST.value(), ex.getFieldError() != null ? ex.getFieldError().getDefaultMessage() : "Generic error");
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorFormat handleNotFoundException(NotFoundException ex) {
        return new ErrorFormat(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(ConcurrentModificationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorFormat handleConcurrentModificationException(ConcurrentModificationException ex) {
        return new ErrorFormat(HttpStatus.BAD_REQUEST.value(),  ex.getMessage());
    }
}
