package com.pietro.blog_back_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pietro.blog_back_spring.entities.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    
}
