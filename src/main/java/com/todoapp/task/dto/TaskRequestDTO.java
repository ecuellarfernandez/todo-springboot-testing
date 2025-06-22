package com.todoapp.task.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public record TaskRequestDTO(
    @NotBlank(message = "El título es obligatorio")
    String title,
    String description,
    LocalDate dueDate
) {}