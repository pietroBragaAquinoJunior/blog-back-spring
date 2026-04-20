package com.pietro.blog_back_spring.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.pietro.blog_back_spring.entities.Role;
import com.pietro.blog_back_spring.enums.ERole;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}