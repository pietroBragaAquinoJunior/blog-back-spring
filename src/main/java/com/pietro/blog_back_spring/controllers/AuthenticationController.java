package com.pietro.blog_back_spring.controllers;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<Void> authenticate(@RequestBody LoginDto dto, HttpServletResponse response) {
        User authenticatedUser = authenticationService.authenticate(dto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        log.info("User: "+ authenticatedUser.getEmail()+" , has been authenticated and received his JWT token.");

        /* 
            https://www.w3tutorials.net/blog/where-to-store-a-jwt-token-properly-and-safely-in-a-web-based-application/#why-jwt-storage-matters
            https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Headers/Set-Cookie
            Adiciona o cookie para o front utilizar nas requisições subsequentes.
            Guardar o JWT de maneira adequada, sem localstorage e essa palhaçada.
            https://medium.com/@AlexanderObregon/cookie-based-auth-in-spring-boot-without-using-sessions-d795c1d530e0

        */

        ResponseCookie cookie = ResponseCookie.from("AUTH_TOKEN", jwtToken)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .sameSite("Strict")
            .maxAge(jwtService.getExpirationTime())
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(null);
    }
}