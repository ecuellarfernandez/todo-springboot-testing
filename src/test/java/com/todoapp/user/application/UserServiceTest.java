package com.todoapp.user.application;

import com.todoapp.user.application.exception.UserAlreadyExistsException;
import com.todoapp.user.domain.User;
import com.todoapp.user.dto.UserRequestDTO;
import com.todoapp.user.dto.UserResponseDTO;
import com.todoapp.user.port.out.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        // Given
        UserRequestDTO request = new UserRequestDTO("testuser", "Test User", "test@email.com", "123456");
        UUID userId = UUID.randomUUID();
        User savedUser = new User(userId, "testuser", "Test User", "test@email.com", "hashedPassword");
        
        when(userRepository.existsByEmail("test@email.com")).thenReturn(false);
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(passwordEncoder.encode("123456")).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // When
        UserResponseDTO result = userService.register(request);

        // Then
        assertThat(result.id()).isEqualTo(userId);
        assertThat(result.username()).isEqualTo("testuser");
        assertThat(result.email()).isEqualTo("test@email.com");
        
        verify(userRepository).existsByEmail("test@email.com");
        verify(userRepository).existsByUsername("testuser");
        verify(passwordEncoder).encode("123456");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // Given
        UserRequestDTO request = new UserRequestDTO("testuser", "Test User", "existing@email.com", "123456");
        when(userRepository.existsByEmail("existing@email.com")).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> userService.register(request))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage("El usuario ya existe");
        
        verify(userRepository).existsByEmail("existing@email.com");
        verify(userRepository, never()).existsByUsername(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenUsernameAlreadyExists() {
        // Given
        UserRequestDTO request = new UserRequestDTO("existinguser", "Test User", "test@email.com", "123456");
        when(userRepository.existsByEmail("test@email.com")).thenReturn(false);
        when(userRepository.existsByUsername("existinguser")).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> userService.register(request))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage("El usuario ya existe");
        
        verify(userRepository).existsByEmail("test@email.com");
        verify(userRepository).existsByUsername("existinguser");
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldGetUserByIdSuccessfully() {
        // Given
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "testuser", "Test User", "test@email.com", "hashedPassword");
        when(userRepository.findById(userId)).thenReturn(user);

        // When
        UserResponseDTO result = userService.getById(userId);

        // Then
        assertThat(result.id()).isEqualTo(userId);
        assertThat(result.username()).isEqualTo("testuser");
        assertThat(result.email()).isEqualTo("test@email.com");
        
        verify(userRepository).findById(userId);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Given
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenThrow(new NoSuchElementException("Usuario no encontrado"));

        // When & Then
        assertThatThrownBy(() -> userService.getById(userId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Usuario no encontrado");
        
        verify(userRepository).findById(userId);
    }
} 