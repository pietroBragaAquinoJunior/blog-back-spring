package com.pietro.blog_back_spring.exceptions;

// https://www.springjavalab.com/2025/05/spring-boot-global-exception-handling.html

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
