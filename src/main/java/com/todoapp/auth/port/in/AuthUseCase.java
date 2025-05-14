package com.todoapp.auth.port.in;

import com.todoapp.auth.dto.LoginRequestDTO;
import com.todoapp.auth.dto.AuthResponseDTO;
import com.todoapp.user.domain.User;

public interface AuthUseCase {
    AuthResponseDTO login(LoginRequestDTO dto);
    User getCurrentUser(String token);
}