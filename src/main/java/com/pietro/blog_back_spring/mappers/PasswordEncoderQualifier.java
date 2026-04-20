package com.pietro.blog_back_spring.mappers;

import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PasswordEncoderQualifier {
    
    private final PasswordEncoder passwordEncoder;

    @Named("encodePassword")
    public String encodePassword(String password){
        return passwordEncoder.encode(password);
    }

}
