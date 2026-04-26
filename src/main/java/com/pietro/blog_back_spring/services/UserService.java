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
            log.info("O usuário com o id: "+id+", não pode ser desabilitado pois ele não existe.");
            return new BadRequestException("Não foi possível desabilitar esse usuário, pois ele não existe.");
        });

        if(!user.isEnabled()){
            log.info("O usuário com o id: "+id+", não pode ser desabilitado novamente.");
            throw new BadRequestException("Não foi possível desabilitar o usuário pois ele já está desabilitado");
        }

        user.setEnabled(false);
        userRepository.save(user);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

  

}
