package com.pietro.blog_back_spring.services;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.pietro.blog_back_spring.dtos.PostDto;
import com.pietro.blog_back_spring.entities.Post;
import com.pietro.blog_back_spring.entities.User;
import com.pietro.blog_back_spring.exceptions.BadRequestException;
import com.pietro.blog_back_spring.mappers.PostMapper;
import com.pietro.blog_back_spring.repositories.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// Os logs sempre ficam no controle, agora só vai ter log no service se houver um erro no service (um throw).
// coloque o log antes do throw. O throw é vago, o log é completo. (para a gente saber e o usuário não.)

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Transactional
    public Post create(PostDto dto, User userLogged) {
        Optional<Post> postOptional = postRepository.findByTitle(dto.getTitle());
        if(postOptional.isPresent()){
            log.info("Não é possível criar duas postagens com mesmo título: "+dto.getTitle()+".");
            throw new BadRequestException("Não é possível criar duas postagens com mesmo título: "+dto.getTitle());
        }
        Post postFromDto = postMapper.toPost(dto);
        postFromDto.setUser(userLogged);
        return postRepository.save(postFromDto);
    }
    
}
