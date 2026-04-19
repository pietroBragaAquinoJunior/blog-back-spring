package com.pietro.blog_back_spring.requests;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;
    private String password;

}
