package com.todoapp.auth.application;

import com.todoapp.auth.dto.LoginRequestDTO;
import com.todoapp.auth.dto.AuthResponseDTO;
import com.todoapp.auth.port.in.AuthUseCase;
import com.todoapp.auth.port.out.JwtService;
import com.todoapp.auth.port.out.UserCredentialsPort;
import com.todoapp.user.application.exception.UserAlreadyLoggedInException;
import com.todoapp.user.domain.User;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
@Service
public class AuthService implements AuthUseCase {

    private final UserCredentialsPort credentials;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;
    // This map is used to store active user sessions
    // In a real application, we would use a more robust solution like Redis or a database
    private final Map<String, String> activeUserSession = new ConcurrentHashMap<>();

    public AuthService(UserCredentialsPort credentials, JwtService jwtService, PasswordEncoder encoder) {
        this.credentials = credentials;
        this.jwtService = jwtService;
        this.encoder = encoder;
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponseDTO login(LoginRequestDTO dto) {
        User user;
        try {
            user = credentials.findByEmail(dto.email());
        } catch (NoSuchElementException e) {
            throw new BadCredentialsException("Credenciales inválidas");
        }

        if (!encoder.matches(dto.password(), user.getPassword())) {
            throw new BadCredentialsException("Credenciales inválidas");
        }

        if (activeUserSession.containsKey(user.getEmail())) {
            String existingToken = activeUserSession.get(user.getEmail());
            if (jwtService.isValid(existingToken)) {
                throw new UserAlreadyLoggedInException("Ya existe una sesión activa para este usuario");
            }
            activeUserSession.remove(user.getEmail());
        }

        String token = jwtService.generateToken(user);
        activeUserSession.put(user.getEmail(), token);

        return new AuthResponseDTO(token);
    }

    @Override
    public User getCurrentUser(String token) {
        String email = jwtService.extractEmail(token);
        return credentials.findByEmail(email);
    }
}