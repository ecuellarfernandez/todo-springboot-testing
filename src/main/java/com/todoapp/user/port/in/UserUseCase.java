package com.todoapp.user.port.in;

import com.todoapp.user.dto.UserRequestDTO;
import com.todoapp.user.dto.UserResponseDTO;

import java.util.UUID;

public interface UserUseCase {
    UserResponseDTO register(UserRequestDTO dto);
    UserResponseDTO getById(UUID id);
}
