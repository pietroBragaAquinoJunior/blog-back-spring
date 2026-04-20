package com.pietro.blog_back_spring.services;

import org.springframework.stereotype.Service;
import com.pietro.blog_back_spring.entities.User;
import com.pietro.blog_back_spring.exceptions.BadRequestException;
import com.pietro.blog_back_spring.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

// Os logs sempre ficam no controle, agora só vai ter log no service se houver um erro no service (um throw).
// coloque o log antes do throw. O throw é vago, o log é completo. (para a gente saber e o usuário não.)

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void disableUser(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> {
            // Mensagem boa para a gente entender no console o que aconteceu. (caso haja erro)
            log.info("The user with id: "+id+", cannot be disabled because he doesn't exist.");
            // Mensagem para dar uma vaga noção ao usuario, quanto menos ele souber melhor.
            return new BadRequestException("It's not possible to disable the user with id: "+id+".");
        });

        if(!user.isEnabled()){
            // Mensagem boa para a gente entender no console o que aconteceu. (caso haja erro)
            log.info("The user with id: "+id+", cannot be disabled because he is disabled already.");
            // Mensagem para dar uma vaga noção ao usuario, quanto menos ele souber melhor.
            throw new BadRequestException("It's not possible to disable the user with id: "+id+".");
        }

        user.setEnabled(false);
        userRepository.save(user);
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }
}
