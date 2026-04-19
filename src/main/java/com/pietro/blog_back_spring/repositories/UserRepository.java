package com.pietro.blog_back_spring.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pietro.blog_back_spring.entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
