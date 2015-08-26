package com.aabplastic.backoffice.exceptions;

import org.springframework.validation.ObjectError;

import java.util.List;

public class RestApiErrorResponse {

    private String errorMessage;

    private List<RestApiError> errors;

    public RestApiErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public RestApiErrorResponse(String errorMessage, List<RestApiError> errors) {
        this.errorMessage = errorMessage;
        this.errors = errors;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<RestApiError> getErrors() {
        return errors;
    }

    public void setErrors(List<RestApiError> errors) {
        this.errors = errors;
    }
}
