package com.todoapp.auth.port.out;

import com.todoapp.user.domain.User;

public interface JwtEncoder {
    String generateToken(User user);
    boolean validateToken(String token);
    String extractUsername(String token);
}