package com.todoapp.project.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProjectResponseDTO(
    UUID id,
    String name,
    String description,
    UUID userId,
    LocalDateTime createdAt
) {}