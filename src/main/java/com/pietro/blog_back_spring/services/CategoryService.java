package com.pietro.blog_back_spring.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.pietro.blog_back_spring.entities.Category;
import com.pietro.blog_back_spring.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    
    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public Category create(String name){
        Category category = Category.builder().name(name).build();
        return categoryRepository.save(category);
    }

}
