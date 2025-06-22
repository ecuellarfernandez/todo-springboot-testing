package com.todoapp.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO (
        @NotBlank(message = "Introduce el email")
        @Email(message = "Email no es válido")
        String email,
        @NotBlank(message = "Introduce la contraseña")
        String password
) {
}
