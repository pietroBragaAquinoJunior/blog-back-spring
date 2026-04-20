package com.pietro.blog_back_spring.configs;

import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.pietro.blog_back_spring.entities.Permission;
import com.pietro.blog_back_spring.entities.Role;
import com.pietro.blog_back_spring.entities.User;
import com.pietro.blog_back_spring.enums.ERole;
import com.pietro.blog_back_spring.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostConstruct
    public void seedRolesAndPermissions() {
        if (userRepository.findByEmail("pietro@gmail.com").isPresent()) return;
        Permission disableUser = Permission.builder().name("DISABLE_USER").build();
        Role adminRole = Role.builder().name(ERole.ADMIN).permissions(Set.of(disableUser)).build();
        Role moderatorRole = Role.builder().name(ERole.MODERATOR).permissions(Set.of(disableUser)).build();
        Role userRole = Role.builder().name(ERole.USER).build();
        User pietro = User.builder().fullName("Piêtro")
            .email("pietro@gmail.com")
            .password(passwordEncoder.encode("123"))
            .roles(Set.of(adminRole))
            .enabled(true)
            .build();
        User ismael = User.builder().fullName("Ismael")
            .email("ismael@gmail.com")
            .password(passwordEncoder.encode("123"))
            .roles(Set.of(moderatorRole))
            .enabled(true)
            .build();
        User nizar =  User.builder().fullName("Nizar")
            .email("nizar@gmail.com")
            .password(passwordEncoder.encode("123"))
            .roles(Set.of(userRole))
            .enabled(true)
            .build();
        userRepository.saveAll(Set.of(pietro, ismael, nizar));
    }

}