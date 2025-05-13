package com.todoapp.auth.port.in;

import com.todoapp.auth.dto.LoginRequestDTO;
import com.todoapp.auth.dto.AuthResponseDTO;

public interface AuthUseCase {
    AuthResponseDTO login(LoginRequestDTO dto);
}