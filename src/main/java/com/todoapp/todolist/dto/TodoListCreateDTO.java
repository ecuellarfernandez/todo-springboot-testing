package com.todoapp.todolist.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record TodoListCreateDTO(
        @NotBlank(message = "El nombre es obligatorio")
        String name,
        @NotBlank(message = "El ID del proyecto es obligatorio")
        UUID projectId
) {}
