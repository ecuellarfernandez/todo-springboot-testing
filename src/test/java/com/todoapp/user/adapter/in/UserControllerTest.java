package com.todoapp.user.adapter.in;

import com.todoapp.user.dto.UserRequestDTO;
import com.todoapp.user.dto.UserResponseDTO;
import com.todoapp.user.port.in.UserUseCase;
import com.todoapp.user.application.exception.UserAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.todoapp.config.JwtFilter;
import com.todoapp.auth.port.out.JwtEncoder;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserUseCase userUseCase;

    @MockitoBean
    private JwtFilter jwtFilter;

    @MockitoBean
    private JwtEncoder jwtEncoder;

    @Test
    void shouldRegisterUserSuccessfully() throws Exception {
        UUID userId = UUID.randomUUID();
        UserResponseDTO response = new UserResponseDTO(userId, "testuser", "test@email.com");
        Mockito.when(userUseCase.register(any(UserRequestDTO.class))).thenReturn(response);

        String json = """
            {
                "username": "testuser",
                "name": "Test User",
                "email": "test@email.com",
                "password": "123456A!"
            }
        """;

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@email.com"));
    }

    @Test
    void shouldReturn409WhenEmailAlreadyExists() throws Exception {
        Mockito.when(userUseCase.register(any(UserRequestDTO.class)))
                .thenThrow(new UserAlreadyExistsException("El usuario ya existe"));

        String json = """
            {
                "username": "testuser",
                "name": "Test User",
                "email": "existing@email.com",
                "password": "123456"
            }
        """;

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldReturn409WhenUsernameAlreadyExists() throws Exception {
        Mockito.when(userUseCase.register(any(UserRequestDTO.class)))
                .thenThrow(new UserAlreadyExistsException("El usuario ya existe"));

        String json = """
            {
                "username": "existinguser",
                "name": "Test User",
                "email": "test@email.com",
                "password": "123456"
            }
        """;

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldReturn400OnInvalidRegistrationData() throws Exception {
        String json = """
            {
                "username": "",
                "name": "",
                "email": "invalid-email",
                "password": "123"
            }
        """;

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetUserByIdSuccessfully() throws Exception {
        UUID userId = UUID.randomUUID();
        UserResponseDTO response = new UserResponseDTO(userId, "testuser", "test@email.com");
        Mockito.when(userUseCase.getById(eq(userId))).thenReturn(response);

        mockMvc.perform(get("/api/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("test@email.com"));
    }

    @Test
    void shouldReturn404WhenUserNotFound() throws Exception {
        UUID userId = UUID.randomUUID();
        Mockito.when(userUseCase.getById(eq(userId)))
                .thenThrow(new NoSuchElementException("Usuario no encontrado"));

        mockMvc.perform(get("/api/users/" + userId))
                .andExpect(status().isNotFound());
    }
} 