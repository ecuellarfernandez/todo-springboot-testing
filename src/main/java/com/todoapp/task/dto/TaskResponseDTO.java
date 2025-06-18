package com.todoapp.task.dto;
import java.time.LocalDateTime;
import java.util.UUID;

public record TaskResponseDTO(
        UUID id,
        String title,
        String description,
        boolean completed,
        LocalDateTime dueDate,
        UUID todoListId
) {}