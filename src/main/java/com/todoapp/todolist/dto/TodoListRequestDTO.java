package com.todoapp.todolist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TodoListRequestDTO(
        @NotBlank(message = "El nombre es obligatorio")
        String name,
        @NotNull(message = "El ID del proyecto es obligatorio")
        UUID projectId
){}
