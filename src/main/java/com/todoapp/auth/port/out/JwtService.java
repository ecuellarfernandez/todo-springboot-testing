package com.todoapp.auth.port.out;

import com.todoapp.user.domain.User;

public interface JwtService {
    String generateToken(User user);
    boolean isValid(String token);
    boolean isTokenValid(String token, User user);
    String extractUsername(String token);
    String extractEmail(String token);
}
