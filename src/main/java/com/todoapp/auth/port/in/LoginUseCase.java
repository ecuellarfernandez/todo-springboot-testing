package com.todoapp.auth.port.in;

import com.todoapp.auth.dto.LoginRequestDTO;
import com.todoapp.auth.dto.AuthResponseDTO;

public interface LoginUseCase {
    AuthResponseDTO login(LoginRequestDTO dto);
}