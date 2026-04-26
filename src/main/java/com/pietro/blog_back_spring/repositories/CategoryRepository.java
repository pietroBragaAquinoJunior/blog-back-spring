package com.pietro.blog_back_spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pietro.blog_back_spring.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}
