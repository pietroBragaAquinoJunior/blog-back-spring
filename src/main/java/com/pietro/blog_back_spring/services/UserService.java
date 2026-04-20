package com.pietro.blog_back_spring.services;

import org.springframework.stereotype.Service;
import com.pietro.blog_back_spring.entities.User;
import com.pietro.blog_back_spring.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void disableUser(Long id){
        User user = userRepository.findById(id).orElseThrow();
        user.setEnabled(false);
        userRepository.save(user);
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }
}
