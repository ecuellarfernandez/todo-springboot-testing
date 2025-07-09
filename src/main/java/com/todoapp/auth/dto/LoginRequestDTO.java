package com.todoapp.auth.dto;

import com.todoapp.common.validation.*;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO (
        @NotBlank(message = "Introduce el email")
        @ValidEmail
        @NoWhitespace
        String email,
        @NotBlank(message = "Introduce la contraseña")
        @NoWhitespace
        String password
) {
}
