package com.pietro.blog_back_spring.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pietro.blog_back_spring.dtos.LoginDto;
import com.pietro.blog_back_spring.dtos.RegisterDto;
import com.pietro.blog_back_spring.dtos.ResetPasswordDto;
import com.pietro.blog_back_spring.entities.User;
import com.pietro.blog_back_spring.exceptions.SuccessResponse;
import com.pietro.blog_back_spring.services.AuthenticationService;
import com.pietro.blog_back_spring.services.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    @Value("${security.cookie.expiration-time}")
    private long cookieMaxAge;

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse> register(@Valid @RequestBody RegisterDto dto) {
        User registeredUser = authenticationService.register(dto);
        log.info("Usuário: " + registeredUser.getEmail() + " , foi registrado com sucesso.");
        return ResponseEntity.ok(new SuccessResponse("Usuário foi criado com sucesso."));
    }

    @PostMapping("/login")
    public ResponseEntity<User> authenticate(@Valid @RequestBody LoginDto dto, HttpServletResponse response) {
        User authenticatedUser = authenticationService.authenticate(dto);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        /*
         * https://www.w3tutorials.net/blog/where-to-store-a-jwt-token-properly-and-
         * safely-in-a-web-based-application/#why-jwt-storage-matters
         * https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Headers/Set-
         * Cookie
         * Adiciona o cookie para o front utilizar nas requisições subsequentes.
         * Guardar o JWT de maneira adequada, sem localstorage e essa palhaçada.
         * https://medium.com/@AlexanderObregon/cookie-based-auth-in-spring-boot-without
         * -using-sessions-d795c1d530e0
         * 
         */

        ResponseCookie cookie = ResponseCookie.from("AUTH_TOKEN", jwtToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Strict")
                .maxAge(cookieMaxAge)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        log.info("Usuário: " + authenticatedUser.getEmail() + " , foi autenticado e recebeu o seu cookie seguro com token jwt.");

        return ResponseEntity.ok(authenticatedUser);
    }

    @PostMapping("/reset-password/email/{email}")
    public ResponseEntity<SuccessResponse> resetPasswordFirst(@PathVariable String email) {
        try {
            authenticationService.createPasswordResetTokenForUserAndSendEmail(email);
        } catch (Exception error) {
            log.info(error.getMessage());
            error.printStackTrace();
            return ResponseEntity.ok(new SuccessResponse("O email será enviado caso ele esteja cadastrado."));
        }

        log.info("Um token para resetar senha foi enviado para esse email [1]: "+ email);
        return ResponseEntity.ok(new SuccessResponse("O email será enviado caso ele esteja cadastrado."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<SuccessResponse> resetPasswordSecond(@Valid @RequestBody ResetPasswordDto dto) {
        try {
        authenticationService.validateTokenAndSaveNewPasswordForUser(dto.getToken(), dto.getNewPassword());
        } catch (Exception error) {
            log.info(error.getMessage());
            error.printStackTrace();
            return ResponseEntity.ok(new SuccessResponse("A senha será resetada caso as informações estejam corretas."));
        }
        log.info("O token: " + dto.getToken() + " foi utilizado, e a senha foi resetada com sucesso.");
        return ResponseEntity.ok(new SuccessResponse("A senha será resetada caso as informações estejam corretas."));
    }

}