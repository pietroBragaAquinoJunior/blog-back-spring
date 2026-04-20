package com.pietro.blog_back_spring.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pietro.blog_back_spring.entities.User;
import com.pietro.blog_back_spring.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('DISABLE_USER')")
    @PostMapping("/disable/id/{id}")
    public ResponseEntity<Void> disableUser(@PathVariable Long id){
        userService.disableUser(id);
        User userLogged = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("User: "+ userLogged.getEmail() + " disabled the user with id: "+ id + ", with success.");
        return ResponseEntity.ok().body(null);
    }

}
