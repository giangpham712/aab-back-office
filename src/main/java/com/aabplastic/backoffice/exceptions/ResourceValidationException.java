package com.aabplastic.backoffice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "There are validation errors")
public class ResourceValidationException extends RuntimeException {

    private String errorMessage;

    private List<ObjectError> errors;

    public ResourceValidationException(String errorMessage, List<ObjectError> errors) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.errors = errors;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<ObjectError> getErrors() {
        return errors;
    }

    public void setErrors(List<ObjectError> errors) {
        this.errors = errors;
    }
}
