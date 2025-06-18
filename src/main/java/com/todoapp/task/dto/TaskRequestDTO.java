package com.todoapp.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public record TaskRequestDTO(
    @NotBlank(message = "El título es obligatorio")
    String title,
    String description,
    LocalDateTime dueDate,
    @NotNull(message = "El ID de la lista es obligatorio")
    UUID todoListId
) {}