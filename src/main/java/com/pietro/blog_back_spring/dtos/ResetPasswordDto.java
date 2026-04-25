package com.pietro.blog_back_spring.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordDto {

    @NotBlank(message = "O token é obrigatório.")
    @Size(min = 5, max = 255, message = "O token deve ter entre 5 e 255 caracteres.")
    private String token;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 9, max = 255, message = "A senha deve ter entre 9 e 255 caracteres.")
    private String newPassword;

}
