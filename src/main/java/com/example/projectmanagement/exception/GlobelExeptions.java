package com.example.projectmanagement.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobelExeptions {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorDetails> userExceptionHandler(Exception e){}

}
