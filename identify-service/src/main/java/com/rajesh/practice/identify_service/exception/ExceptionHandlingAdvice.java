package com.rajesh.practice.identify_service.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingAdvice {


    @ExceptionHandler
    public String UserNotFoundExceptionHandling(UserNotFoundException userNotFoundException){
        return userNotFoundException.getMessage();
    }
}
