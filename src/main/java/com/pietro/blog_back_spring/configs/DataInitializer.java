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

        Permission readUserPermission = new Permission(null, "READ_USER");
        Permission deleteUserPermission = new Permission(null, "DELETE_USER");

        Role admin = new Role();
        admin.setName(ERole.ADMIN);
        admin.setPermissions(Set.of(readUserPermission, deleteUserPermission));

        Role user = new Role();
        user.setName(ERole.USER);

        userRepository.save(new User(null, "Piêtro Braga", "pietro@gmail.com", passwordEncoder.encode("123"), null, null, Set.of(admin)));
        userRepository.save(new User(null, "Nizar Mohsen", "nizar@gmail.com", passwordEncoder.encode("123"), null, null, Set.of(user)));

    }

}