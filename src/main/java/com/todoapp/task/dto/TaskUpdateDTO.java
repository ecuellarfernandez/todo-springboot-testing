package com.todoapp.task.dto;

import java.time.LocalDateTime;

public record TaskUpdateDTO(
        String title,
        String description,
        LocalDateTime dueDate
) {}