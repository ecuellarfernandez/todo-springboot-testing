package com.todoapp.project.dto;

import jakarta.validation.constraints.NotBlank;

public record ProjectRequestDTO(
    @NotBlank(message = "El nombre es obligatorio")
    String name,
    String description
) {}