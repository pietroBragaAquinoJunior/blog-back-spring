package com.pietro.blog_back_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pietro.blog_back_spring.entities.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
