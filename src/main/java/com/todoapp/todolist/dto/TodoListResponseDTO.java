package com.todoapp.todolist.dto;

import java.util.UUID;

public record TodoListResponseDTO(
        UUID id,
        String name,
        UUID projectId
) {}
