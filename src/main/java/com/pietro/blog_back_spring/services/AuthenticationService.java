package com.pietro.blog_back_spring.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.pietro.blog_back_spring.dtos.LoginDto;
import com.pietro.blog_back_spring.dtos.RegisterDto;
import com.pietro.blog_back_spring.entities.Role;
import com.pietro.blog_back_spring.entities.User;
import com.pietro.blog_back_spring.enums.ERole;
import com.pietro.blog_back_spring.exceptions.BadRequestException;
import com.pietro.blog_back_spring.repositories.RoleRepository;
import com.pietro.blog_back_spring.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.Set;

// Os logs sempre ficam no controle, agora só vai ter log no service se houver um erro no service (um throw).
// coloque o log antes do throw. O throw é vago, o log é completo. (para a gente saber e o usuário não.)

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public User register(RegisterDto dto) throws BadRequestException {

        Optional<User> user = userRepository.findByEmail(dto.getEmail());
        if(user.isPresent()){
            // Mensagem boa para a gente entender no console o que aconteceu. (caso haja erro)
            log.info("Cannot create user again with the same email: "+dto.getEmail()+".");
            // Mensagem para dar uma vaga noção ao usuario, quanto menos ele souber melhor.
            throw new BadRequestException("It's not possible to create user: "+dto.getEmail()+", with this information.");
        } 
        

        Set<Role> roles = Set.of(roleRepository.findByName(ERole.USER).orElseThrow());
        return userRepository.save(
            User.builder().fullName(dto.getFullName())
            .email(dto.getEmail())
            .password(passwordEncoder.encode(dto.getPassword()))
            .roles(roles)
            .enabled(true).build()
        );
    }

    public User authenticate(LoginDto input) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                input.getEmail(),
                input.getPassword()
            )
        );
        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }
}
