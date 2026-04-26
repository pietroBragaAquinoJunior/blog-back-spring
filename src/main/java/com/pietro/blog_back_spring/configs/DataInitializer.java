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
        Permission canDisableUser = Permission.builder().name("CAN_DISABLE_USER").build();

        Permission canCreatePost = Permission.builder().name("CAN_CREATE_POST").build();
        Permission canCreateCategories = Permission.builder().name("CAN_CREATE_CATEGORIES").build();

        Permission canSeePosts = Permission.builder().name("CAN_SEE_POSTS").build();
        Permission canSeeCategories = Permission.builder().name("CAN_SEE_CATEGORIES").build();

        Role adminRole = Role.builder().name(ERole.ADMIN).permissions(Set.of(
            canDisableUser,
      
            canSeePosts,
            canSeeCategories,
            
            canCreatePost,
            canCreateCategories
        )).build();
        Role moderatorRole = Role.builder().name(ERole.MODERATOR).permissions(Set.of(
            canSeePosts,
            canSeeCategories
        )).build();
        Role userRole = Role.builder().name(ERole.USER).permissions(Set.of(
            canSeePosts,
            canSeeCategories
        )).build();
        User pietro = User.builder().fullName("Piêtro")
            .email("pietrobragaaquino@gmail.com")
            .password(passwordEncoder.encode("172983456"))
            .roles(Set.of(adminRole))
            .enabled(true)
            .build();
        User ismael = User.builder().fullName("Ismael")
            .email("ismael@gmail.com")
            .password(passwordEncoder.encode("172983456"))
            .roles(Set.of(moderatorRole))
            .enabled(true)
            .build();
        User nizar =  User.builder().fullName("Carol")
            .email("caroline-aquino@hotmail.com")
            .password(passwordEncoder.encode("172983456"))
            .roles(Set.of(userRole))
            .enabled(true)
            .build();
        userRepository.saveAll(Set.of(pietro, ismael, nizar));
    }

}