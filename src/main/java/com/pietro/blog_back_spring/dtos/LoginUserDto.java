package com.pietro.blog_back_spring.dtos;

import lombok.Data;

@Data
public class LoginUserDto {
    private String email;
    private String password;
}
