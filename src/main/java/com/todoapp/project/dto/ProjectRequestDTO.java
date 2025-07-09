package com.todoapp.project.dto;

import com.todoapp.common.validation.*;
import jakarta.validation.constraints.NotBlank;

public record ProjectRequestDTO(
    @NotBlank(message = "El nombre es obligatorio")
    @NoWhitespace
    @MinWords(2)
    String name,
    @NoWhitespace
    String description
) {}