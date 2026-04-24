package com.pietro.blog_back_spring.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import com.pietro.blog_back_spring.dtos.LoginDto;
import com.pietro.blog_back_spring.dtos.RegisterDto;
import com.pietro.blog_back_spring.entities.Role;
import com.pietro.blog_back_spring.entities.User;
import com.pietro.blog_back_spring.enums.ERole;
import com.pietro.blog_back_spring.exceptions.BadRequestException;
import com.pietro.blog_back_spring.mappers.UserMapper;
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
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    public User register(RegisterDto registerDto) throws BadRequestException {

        Optional<User> user = userRepository.findByEmail(registerDto.getEmail());
        if(user.isPresent()){
            log.info("Cannot create user again with the same email: "+registerDto.getEmail()+".");
            throw new BadRequestException("It's not possible to create user: "+registerDto.getEmail()+", with this information.");
        } 
        
        User userFromRegisterDto = userMapper.toUser(registerDto);
        Set<Role> roles = Set.of(roleRepository.findByName(ERole.USER).orElseThrow());
        userFromRegisterDto.setRoles(roles);
        userFromRegisterDto.setEnabled(true);

        return userRepository.save(userFromRegisterDto);
    }

    public User authenticate(LoginDto loginDto) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()
            )
        );
        return userRepository.findByEmail(loginDto.getEmail()).orElseThrow();
    }
}
