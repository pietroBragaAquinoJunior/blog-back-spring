package com.pietro.blog_back_spring.services;


import org.springframework.stereotype.Service;

import com.pietro.blog_back_spring.entities.User;
import com.pietro.blog_back_spring.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
}
