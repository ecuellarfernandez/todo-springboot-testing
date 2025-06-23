package com.todoapp.auth.adapter.in;

import com.todoapp.auth.dto.AuthResponseDTO;
import com.todoapp.auth.dto.LoginRequestDTO;
import com.todoapp.auth.port.in.LoginUseCase;
import com.todoapp.auth.port.in.UserContextUseCase;
import com.todoapp.user.domain.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.todoapp.config.JwtFilter;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LoginUseCase loginUseCase;

    @MockitoBean
    private UserContextUseCase userContextUseCase;

    @MockitoBean
    private JwtFilter jwtFilter;

    @MockitoBean
    private com.todoapp.auth.port.out.JwtEncoder jwtEncoder;

    @Test
    void shouldLoginSuccessfully() throws Exception {
        Mockito.when(loginUseCase.login(any(LoginRequestDTO.class)))
                .thenReturn(new AuthResponseDTO("token123"));

        String json = """
            {"email": "test@email.com", "password": "123456"}
        """;

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token123"));
    }

    @Test
    void shouldReturnCurrentUser() throws Exception {
        User user = new User(UUID.randomUUID(), "user", "name", "mail", "pass");
        Mockito.when(userContextUseCase.getCurrentUser(eq("token123"))).thenReturn(user);

        mockMvc.perform(get("/api/auth/me")
                .header("Authorization", "Bearer token123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("mail"));
    }

    @Test
    void shouldReturn401OnInvalidCredentials() throws Exception {
        Mockito.when(loginUseCase.login(any(LoginRequestDTO.class)))
                .thenThrow(new org.springframework.security.authentication.BadCredentialsException("Credenciales inválidas"));

        String json = """
            {"email": "test@email.com", "password": "wrongpass"}
        """;

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn400OnInvalidLoginData() throws Exception {
        String json = """
            {"email": "", "password": ""}
        """;

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn401WhenNoTokenProvided() throws Exception {
        mockMvc.perform(get("/api/auth/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn401WhenTokenIsInvalid() throws Exception {
        Mockito.when(userContextUseCase.getCurrentUser(any())).thenThrow(new org.springframework.security.authentication.BadCredentialsException("Token inválido"));

        mockMvc.perform(get("/api/auth/me")
                .header("Authorization", "Bearer invalidtoken"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn400OnInvalidRegistrationData() throws Exception {
        String json = """
                {
                    "username": "",
                    "name": "",
                    "email": "email-no-valid",
                    "password": "123"
                }
                """;

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

}