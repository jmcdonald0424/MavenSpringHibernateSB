package com.mysandbox.mvnspringhibernatesb.exception;

import org.springframework.web.client.RestClientException;

public class SandboxUserNotFoundException extends RestClientException {
    
    public SandboxUserNotFoundException(String message){
        super(message);
    }
}