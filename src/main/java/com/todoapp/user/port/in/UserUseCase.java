package com.todoapp.user.port.in;

import com.todoapp.user.dto.UserRequestDTO;
import com.todoapp.user.dto.UserResponseDTO;

public interface UserUseCase {
    UserResponseDTO register(UserRequestDTO dto);
    UserResponseDTO getById(Long id);
}
