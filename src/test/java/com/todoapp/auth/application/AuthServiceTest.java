package com.todoapp.auth.application;

import com.todoapp.auth.dto.AuthResponseDTO;
import com.todoapp.auth.dto.LoginRequestDTO;
import com.todoapp.auth.port.out.JwtEncoder;
import com.todoapp.auth.port.out.UserCredentialsPort;
import com.todoapp.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    UserCredentialsPort credentials;

    @Mock
    JwtEncoder jwtEncoder;

    @Mock
    PasswordEncoder encoder;

    AuthService service;

    @BeforeEach
    void setUp() {
        service = new AuthService(credentials, jwtEncoder, encoder);
    }

    @Test
    void shouldLoginWithValidCredentials() {
        LoginRequestDTO request = new LoginRequestDTO("mail", "pass");
        User user = new User(1L, "user", "name", "mail", "hashed");
        when(credentials.findByEmail("mail")).thenReturn(user);
        when(encoder.matches("pass", "hashed")).thenReturn(true);
        when(jwtEncoder.generateToken(user)).thenReturn("token");
        AuthResponseDTO response = service.login(request);
        assertThat(response.token()).isEqualTo("token");
    }

    @Test
    void shouldThrowOnInvalidCredentials() {
        LoginRequestDTO request = new LoginRequestDTO("mail", "wrong");
        User user = new User(1L, "user", "name", "mail", "hashed");
        when(credentials.findByEmail("mail")).thenReturn(user);
        when(encoder.matches("wrong", "hashed")).thenReturn(false);
        assertThatThrownBy(() -> service.login(request)).isInstanceOf(BadCredentialsException.class);
    }
}
