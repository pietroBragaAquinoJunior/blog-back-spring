package com.pietro.blog_back_spring.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pietro.blog_back_spring.dtos.PostDto;
import com.pietro.blog_back_spring.entities.Post;
import com.pietro.blog_back_spring.entities.User;
import com.pietro.blog_back_spring.services.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    
    private final PostService postService;

    @PreAuthorize("hasAuthority('CAN_CREATE_POST')")
    @PostMapping
    public ResponseEntity<Void> createPost(@Valid @RequestBody PostDto dto){
        User userLogged = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = postService.create(dto, userLogged);
        log.info("User: "+userLogged.getEmail()+", created a new post: "+post.getTitle());
        return ResponseEntity.ok(null);
    }

}
