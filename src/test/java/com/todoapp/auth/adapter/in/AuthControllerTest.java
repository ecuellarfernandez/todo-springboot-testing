package com.todoapp.auth.adapter.in;

import com.todoapp.auth.dto.AuthResponseDTO;
import com.todoapp.auth.dto.LoginRequestDTO;
import com.todoapp.auth.port.in.LoginUseCase;
import com.todoapp.auth.port.in.UserContextUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.todoapp.config.JwtFilter;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc
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
                .andExpect(jsonPath("$.token").exists());
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

}