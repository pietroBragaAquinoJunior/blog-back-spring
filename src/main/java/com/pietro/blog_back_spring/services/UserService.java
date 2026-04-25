package com.pietro.blog_back_spring.services;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.pietro.blog_back_spring.entities.User;
import com.pietro.blog_back_spring.exceptions.BadRequestException;
import com.pietro.blog_back_spring.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// Os logs sempre ficam no controle, agora só vai ter log no service se houver um erro no service (um throw).
// coloque o log antes do throw. O throw é vago, o log é completo. (para a gente saber e o usuário não.)

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void disable(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> {
            log.info("The user with id: "+id+", cannot be disabled because he doesn't exist.");
            return new BadRequestException("It's not possible to disable the user with id: "+id+".");
        });

        if(!user.isEnabled()){
            log.info("The user with id: "+id+", cannot be disabled because he is disabled already.");
            throw new BadRequestException("It's not possible to disable the user with id: "+id+".");
        }

        user.setEnabled(false);
        userRepository.save(user);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

  

}
