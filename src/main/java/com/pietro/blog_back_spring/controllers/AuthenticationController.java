package com.pietro.blog_back_spring.controllers;

import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pietro.blog_back_spring.dtos.LoginDto;
import com.pietro.blog_back_spring.dtos.RegisterDto;
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
    public ResponseEntity<Void> register(@RequestBody RegisterDto dto) throws BadRequestException {
        User registeredUser = authenticationService.register(dto);
        log.info("User: "+ registeredUser.getEmail()+" , has been registered.");
        return ResponseEntity.ok(null);
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody LoginDto dto) {
        User authenticatedUser = authenticationService.authenticate(dto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        log.info("User: "+ authenticatedUser.getEmail()+" , has been authenticated and received his JWT token.");
        return ResponseEntity.ok(jwtToken);
    }
}