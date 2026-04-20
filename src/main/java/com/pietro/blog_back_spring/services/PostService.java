package com.pietro.blog_back_spring.services;

import org.springframework.stereotype.Service;
import com.pietro.blog_back_spring.dtos.PostDto;
import com.pietro.blog_back_spring.entities.Post;
import com.pietro.blog_back_spring.entities.User;
import com.pietro.blog_back_spring.repositories.PostRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post createByDto(PostDto dto, User userLogged) {
        return postRepository.save(
            Post.builder()
            .title(dto.getTitle())
            .description(dto.getDescription())
            .html(dto.getHtml())
            .published(dto.isPublished())
            .user(userLogged)
            .build()
        );
    }
    
}
