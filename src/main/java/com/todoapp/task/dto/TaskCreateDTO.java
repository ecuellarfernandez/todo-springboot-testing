package com.todoapp.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record TaskCreateDTO(
    @NotBlank(message = "El título es obligatorio")
    String title,
    String description,
    LocalDate dueDate,
    @NotNull(message = "El ID de la lista es obligatorio")
    UUID todoListId,
    @NotNull(message = "El ID del proyecto es obligatorio")
    UUID projectId
) {}