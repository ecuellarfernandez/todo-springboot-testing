package com.todoapp.auth.port.out;

import com.todoapp.user.domain.User;

public interface JwtService {
    String generateToken(User user);
    boolean isValid(String token);
    String extractUsername(String token);
}
