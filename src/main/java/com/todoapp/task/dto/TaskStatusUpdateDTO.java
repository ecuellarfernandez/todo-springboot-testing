package com.todoapp.task.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskStatusUpdateDTO(
        boolean completed
) {}