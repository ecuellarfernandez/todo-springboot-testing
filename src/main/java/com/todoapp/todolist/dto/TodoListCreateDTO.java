package com.todoapp.todolist.dto;

import java.util.UUID;

public record TodoListCreateDTO(
        String name,
        UUID projectId
) {}
