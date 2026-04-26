package com.pietro.blog_back_spring.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.pietro.blog_back_spring.dtos.LoginDto;
import com.pietro.blog_back_spring.dtos.RegisterDto;
import com.pietro.blog_back_spring.entities.PasswordResetToken;
import com.pietro.blog_back_spring.entities.Role;
import com.pietro.blog_back_spring.entities.User;
import com.pietro.blog_back_spring.enums.ERole;
import com.pietro.blog_back_spring.exceptions.BadRequestException;
import com.pietro.blog_back_spring.mappers.UserMapper;
import com.pietro.blog_back_spring.repositories.PasswordResetTokenRepository;
import com.pietro.blog_back_spring.repositories.RoleRepository;
import com.pietro.blog_back_spring.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

// Os logs sempre ficam no controle, agora só vai ter log no service se houver um erro no service (um throw).
// coloque o log antes do throw. O throw é vago, o log é completo. (para a gente saber e o usuário não.)

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Value("${security.reset-password.expiration-time}")
    private Long expirationSeconds;

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final EmailService emailService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register(RegisterDto registerDto) {
        Optional<User> user = userRepository.findByEmail(registerDto.getEmail());
        if (user.isPresent()) {
            log.info("Não é possível criar dois usuários com mesmo email: " + registerDto.getEmail());
            throw new BadRequestException("Não foi possível criar o usuário: " + registerDto.getEmail() + " com essa informação");
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
                        loginDto.getPassword()));
        return userRepository.findByEmail(loginDto.getEmail()).orElseThrow();
    }

    @Transactional
    public void createPasswordResetTokenForUserAndSendEmail(String email) throws MessagingException  {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            log.info("Não é possível resetar a senha do usuário [1] se o email não foi encontrado: "+ email);
            throw new BadRequestException("Não foi possível criar o token.");
        }
        User user = userOptional.get();
        String token = UUID.randomUUID().toString();
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationSeconds);
        PasswordResetToken passwordResetToken = PasswordResetToken.builder().uuidToken(token).user(user)
            .expiryDate(expiry).build();
        PasswordResetToken passwordResetTokenSaved = passwordResetTokenRepository.save(passwordResetToken);
        emailService.sendResetEmail(user.getEmail(), passwordResetTokenSaved.getUuidToken());
    }

    @Transactional
    public void validateTokenAndSaveNewPasswordForUser(String token, String newPassword)  {
        Optional<PasswordResetToken> passwordResetTokenOptional = passwordResetTokenRepository.findByUuidToken(token);
        if(passwordResetTokenOptional.isEmpty()){
            log.info("Alguém tentou usar um token inválido para resetar senha [2].");
            throw new BadRequestException("A senha não pôde ser resetada.");
        }
        Optional<User> userOptional = userRepository.findById(passwordResetTokenOptional.get().getUser().getId());
        if(userOptional.isEmpty()){
            log.info("Alguém tentou usar um token para resetar um usuário que não foi encontrado no banco. [2]");
            throw new BadRequestException("A senha não pôde ser resetada.");
        }
        User userToUpdate = userOptional.get();
        userToUpdate.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(userToUpdate);
    }
}
