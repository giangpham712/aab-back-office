package com.aabplastic.backoffice.exceptions;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(annotations = Controller.class)
public class MvcExceptionHandlerAdvice {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public String notFound(HttpServletRequest req, Exception e) {
        return "404";
    }

    public String internalError(HttpServletRequest req, Exception e) {
        return "500";
    }
}
