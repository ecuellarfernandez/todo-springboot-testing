package com.todoapp.user.dto;

import com.todoapp.common.validation.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank(message = "El nombre de usuario es obligatorio")
        @ValidUsername
        @NoWhitespace
        String username,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 3, message = "El nombre debe tener al menos 3 caracteres")
        @NoWhitespace
        @MinWords(2)
        String name,

        @NotBlank(message = "El correo electrónico es obligatorio")
        @ValidEmail
        @NoWhitespace
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        @StrongPassword
        @NoWhitespace
        String password
) {}