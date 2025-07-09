package com.todoapp.task.dto;

import com.todoapp.common.validation.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record TaskCreateDTO(
    @NotBlank(message = "El título es obligatorio")
    @NoWhitespace
    @MinWords(2)
    String title,
    @NoWhitespace
    String description,
    @FutureDate
    LocalDate dueDate,
    @NotNull(message = "El ID de la lista es obligatorio")
    UUID todoListId,
    @NotNull(message = "El ID del proyecto es obligatorio")
    UUID projectId
) {}