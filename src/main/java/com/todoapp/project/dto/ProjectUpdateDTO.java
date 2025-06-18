package com.todoapp.project.dto;

import jakarta.validation.constraints.NotBlank;

public record ProjectUpdateDTO(
    String name,
    String description
) {}