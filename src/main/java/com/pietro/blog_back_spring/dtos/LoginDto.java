package com.pietro.blog_back_spring.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @NotBlank(message = "O email é obrigatório.")
    @Size(min = 5, max = 255, message = "O email deve ter entre 5 e 255 caracteres.")
    @Email(message = "O email não segue o padrão esperado.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 9, max = 255, message = "A senha deve ter entre 9 e 255 caracteres.")
    private String password;

}
