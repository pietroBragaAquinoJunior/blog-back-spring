package com.pietro.blog_back_spring.requests;

import lombok.Data;

@Data
public class LoginUserDto {
    private String email;
    private String password;
}