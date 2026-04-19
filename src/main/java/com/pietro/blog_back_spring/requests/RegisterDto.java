package com.pietro.blog_back_spring.requests;

import lombok.Data;

@Data
public class RegisterDto {
    private String email;
    private String password;
    private String fullName;
}
