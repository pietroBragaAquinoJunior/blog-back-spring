package com.pietro.blog_back_spring.requests;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private long expiresIn;
}