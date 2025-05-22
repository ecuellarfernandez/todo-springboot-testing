package com.todoapp.auth.application;

import com.todoapp.auth.dto.LoginRequestDTO;
import com.todoapp.auth.dto.AuthResponseDTO;
import com.todoapp.auth.port.in.AuthUseCase;
import com.todoapp.auth.port.out.JwtEncoder;
import com.todoapp.auth.port.out.UserCredentialsPort;
import com.todoapp.user.domain.User;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class AuthService implements AuthUseCase {

    private final UserCredentialsPort credentials;
    private final JwtEncoder jwtEncoder;
    private final PasswordEncoder encoder;

    public AuthService(UserCredentialsPort credentials, JwtEncoder jwtEncoder, PasswordEncoder encoder) {
        this.credentials = credentials;
        this.jwtEncoder = jwtEncoder;
        this.encoder = encoder;
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponseDTO login(LoginRequestDTO dto) {

        try{
            User user = credentials.findByEmail(dto.email());
            if(!encoder.matches(dto.password(), user.getPassword())) {
                throw new BadCredentialsException("Email o contraseña incorrectos");
            }
            String token = jwtEncoder.generateToken(user);
            return new AuthResponseDTO(token);
        }catch(NoSuchElementException e){
            throw new BadCredentialsException("Email o contraseña incorrectos");
        }

    }

    @Override
    public User getCurrentUser(String token) {
        String email = jwtEncoder.extractUsername(token);
        return credentials.findByEmail(email);
    }
}