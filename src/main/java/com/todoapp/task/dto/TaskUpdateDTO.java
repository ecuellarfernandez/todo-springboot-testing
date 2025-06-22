package com.todoapp.task.dto;

import java.time.LocalDate;

public record TaskUpdateDTO(
        String title,
        String description,
        LocalDate dueDate
) {}