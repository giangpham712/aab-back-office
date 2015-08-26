package com.aabplastic.backoffice.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.stream.Collectors;

@ControllerAdvice(annotations = RestController.class)
public class RestApiExceptionHandlerAdvice {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public RestApiErrorResponse notFound(Exception exception, WebRequest request) {
        return new RestApiErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public RestApiErrorResponse internalServiceError(Exception exception, WebRequest request) {
        return new RestApiErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(value = {ResourceValidationException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public RestApiErrorResponse unprocessableEntity(Exception exception, WebRequest request) {
        ResourceValidationException resourceValidationException = (ResourceValidationException) exception;
        return new RestApiErrorResponse(
                exception.getMessage(),
                resourceValidationException.getErrors().stream().map(x -> {

                    RestApiError error = new RestApiError();
                    error.setCode(x.getCode());
                    error.setDefaultMessage(x.getDefaultMessage());
                    error.setObjectName(x.getObjectName());

                    if (x instanceof FieldError) {
                        FieldError fieldError = (FieldError) x;
                        error.setField(fieldError.getField());
                        error.setRejectedValue(fieldError.getRejectedValue());
                    }

                    return error;
                }).collect(Collectors.toList()));
    }
}
