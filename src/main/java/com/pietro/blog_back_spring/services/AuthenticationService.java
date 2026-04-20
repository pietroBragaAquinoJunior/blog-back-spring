package com.pietro.blog_back_spring.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.pietro.blog_back_spring.dtos.LoginUserDto;
import com.pietro.blog_back_spring.dtos.RegisterUserDto;
import com.pietro.blog_back_spring.entities.Role;
import com.pietro.blog_back_spring.entities.User;
import com.pietro.blog_back_spring.enums.ERole;
import com.pietro.blog_back_spring.exceptions.BadRequestException;
import com.pietro.blog_back_spring.repositories.RoleRepository;
import com.pietro.blog_back_spring.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public User register(RegisterUserDto dto) throws BadRequestException {

        Optional<User> user = userRepository.findByEmail(dto.getEmail());
        if(user.isPresent()){
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

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                input.getEmail(),
                input.getPassword()
            )
        );
        return userRepository.findByEmail(input.getEmail()).orElseThrow();
    }
}
