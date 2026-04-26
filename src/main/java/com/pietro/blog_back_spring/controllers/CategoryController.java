package com.pietro.blog_back_spring.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pietro.blog_back_spring.dtos.CreateCategoryDto;
import com.pietro.blog_back_spring.dtos.PostDto;
import com.pietro.blog_back_spring.entities.Category;
import com.pietro.blog_back_spring.entities.Post;
import com.pietro.blog_back_spring.entities.User;
import com.pietro.blog_back_spring.exceptions.SuccessResponse;
import com.pietro.blog_back_spring.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    
    private final CategoryService categoryService;

    @PreAuthorize("hasAuthority('CAN_SEE_CATEGORIES')")
    @GetMapping
    public ResponseEntity<List<Category>> findAllCategories(){
        return ResponseEntity.ok(categoryService.findAll());
    }

    @PreAuthorize("hasAuthority('CAN_CREATE_CATEGORY')")
    @PostMapping
    public ResponseEntity<SuccessResponse> createPost(@Valid @RequestBody CreateCategoryDto dto){
        User userLogged = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Category category = categoryService.create(dto.getName());
        log.info("Usuário: "+userLogged.getEmail()+", criou uma nova categoria: "+category.getName());
        return ResponseEntity.ok(new SuccessResponse("A categoria foi criada com sucesso."));
    }

}
