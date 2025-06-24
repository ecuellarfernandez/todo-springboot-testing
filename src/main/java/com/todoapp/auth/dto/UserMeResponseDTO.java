package com.todoapp.auth.dto;

import java.util.UUID;

public record UserMeResponseDTO(UUID id, String username, String name, String email) {}
