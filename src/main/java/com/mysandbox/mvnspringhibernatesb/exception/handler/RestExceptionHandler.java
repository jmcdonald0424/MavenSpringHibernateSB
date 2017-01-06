package com.mysandbox.mvnspringhibernatesb.exception.handler;

import com.mysandbox.mvnspringhibernatesb.exception.SandboxUserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@ControllerAdvice(basePackages = {"com.mysandbox.mvnspringhibernatesb.controller"})
public class RestExceptionHandler{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);
    
    @ExceptionHandler(SandboxUserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleSandboxUserNotFoundException(SandboxUserNotFoundException ex){
        LOGGER.debug("Handling 404 error on a sandboxUser entry");
    }
}