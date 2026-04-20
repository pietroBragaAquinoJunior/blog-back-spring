package com.pietro.blog_back_spring.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pietro.blog_back_spring.dtos.LoginResponse;
import com.pietro.blog_back_spring.dtos.LoginUserDto;
import com.pietro.blog_back_spring.dtos.RegisterUserDto;
import com.pietro.blog_back_spring.entities.User;
import com.pietro.blog_back_spring.services.AuthenticationService;
import com.pietro.blog_back_spring.services.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterUserDto dto) {
        User registeredUser = authenticationService.register(dto);
        String jwtToken = jwtService.generateToken(registeredUser);
        LoginResponse loginResponse = LoginResponse.builder().token(jwtToken).expiresIn(jwtService.getExpirationTime()).build();
        User userLogged = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("User: "+ userLogged.getEmail()+" , has been registered and logged in.");
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto dto) {
        User authenticatedUser = authenticationService.authenticate(dto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = LoginResponse.builder().token(jwtToken).expiresIn(jwtService.getExpirationTime()).build();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("User: "+user.getEmail()+", has logged in.");
        return ResponseEntity.ok(loginResponse);
    }
}