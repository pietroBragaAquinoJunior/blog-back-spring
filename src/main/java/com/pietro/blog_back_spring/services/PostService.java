package com.pietro.blog_back_spring.services;

import org.springframework.stereotype.Service;
import com.pietro.blog_back_spring.dtos.PostDto;
import com.pietro.blog_back_spring.entities.Post;
import com.pietro.blog_back_spring.entities.User;
import com.pietro.blog_back_spring.mappers.PostMapper;
import com.pietro.blog_back_spring.repositories.PostRepository;
import lombok.RequiredArgsConstructor;

// Os logs sempre ficam no controle, agora só vai ter log no service se houver um erro no service (um throw).
// coloque o log antes do throw. O throw é vago, o log é completo. (para a gente saber e o usuário não.)

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public Post create(PostDto dto, User userLogged) {
        Post postFromDto = postMapper.toPost(dto);
        postFromDto.setUser(userLogged);
        return postRepository.save(postFromDto);
    }
    
}
