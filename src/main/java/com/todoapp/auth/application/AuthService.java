package com.todoapp.auth.application;

import com.todoapp.auth.dto.LoginRequestDTO;
import com.todoapp.auth.dto.AuthResponseDTO;
import com.todoapp.auth.port.in.AuthUseCase;
import com.todoapp.auth.port.out.JwtService;
import com.todoapp.auth.port.out.UserCredentialsPort;
import com.todoapp.user.domain.User;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService implements AuthUseCase {

    private final UserCredentialsPort credentials;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    public AuthService(UserCredentialsPort credentials, JwtService jwtService, PasswordEncoder encoder) {
        this.credentials = credentials;
        this.jwtService = jwtService;
        this.encoder = encoder;
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponseDTO login(LoginRequestDTO dto) {
        User user = credentials.findByEmail(dto.email());
        if (!encoder.matches(dto.password(), user.getPassword())) {
            throw new BadCredentialsException("Credenciales invalidos");
        }

        String token = jwtService.generateToken(user);

        return new AuthResponseDTO(token);
    }

    @Override
    public User getCurrentUser(String token) {
        String email = jwtService.extractUsername(token);
        return credentials.findByEmail(email);
    }
}