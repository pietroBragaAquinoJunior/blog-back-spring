package com.pietro.blog_back_spring.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {

    @NotBlank(message = "O título é obrigatório.")
    @Size(min = 5, max = 255, message = "O título deve ter entre 5 e 255 caracteres.")
    private String title;

    @NotBlank
    @Size(min = 5, max = 255, message = "A descrição deve ter entre 5 e 255 caracteres.")
    private String description;

    @NotBlank
    @Size(min = 5, message = "O html deve ter no mínimo 5 caracteres.")
    private String html;

    private boolean published;
}
