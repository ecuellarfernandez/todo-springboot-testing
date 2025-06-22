package com.todoapp.task.dto;
import java.time.LocalDate;
import java.util.UUID;

public record TaskResponseDTO(
        UUID id,
        String title,
        String description,
        String completed,
        LocalDate dueDate,
        UUID todoListId,
        UUID projectId
) {}