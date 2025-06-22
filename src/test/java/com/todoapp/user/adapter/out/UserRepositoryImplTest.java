package com.todoapp.user.adapter.out;

import com.todoapp.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private UserMapper userMapper;

    private UserRepositoryImpl userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryImpl(userJpaRepository, userMapper);
    }

    @Test
    void shouldSaveUserSuccessfully() {
        // Given
        UUID userId = UUID.randomUUID();
        User user = new User(null, "testuser", "Test User", "test@email.com", "hashedPassword");
        UserEntity entity = new UserEntity("testuser", "Test User", "test@email.com", "hashedPassword");
        UserEntity savedEntity = new UserEntity("testuser", "Test User", "test@email.com", "hashedPassword");
        User expectedUser = new User(userId, "testuser", "Test User", "test@email.com", "hashedPassword");
        
        savedEntity.setId(userId);
        
        when(userMapper.domainToEntity(user)).thenReturn(entity);
        when(userJpaRepository.save(entity)).thenReturn(savedEntity);
        when(userMapper.entityToDomain(savedEntity)).thenReturn(expectedUser);

        // When
        User result = userRepository.save(user);

        // Then
        assertThat(result).isEqualTo(expectedUser);
        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getUsername()).isEqualTo("testuser");
        assertThat(result.getEmail()).isEqualTo("test@email.com");
        
        verify(userMapper).domainToEntity(user);
        verify(userJpaRepository).save(entity);
        verify(userMapper).entityToDomain(savedEntity);
    }

    @Test
    void shouldFindUserByIdSuccessfully() {
        // Given
        UUID userId = UUID.randomUUID();
        UserEntity entity = new UserEntity("testuser", "Test User", "test@email.com", "hashedPassword");
        entity.setId(userId);
        User expectedUser = new User(userId, "testuser", "Test User", "test@email.com", "hashedPassword");
        
        when(userJpaRepository.findById(userId)).thenReturn(java.util.Optional.of(entity));
        when(userMapper.entityToDomain(entity)).thenReturn(expectedUser);

        // When
        User result = userRepository.findById(userId);

        // Then
        assertThat(result).isEqualTo(expectedUser);
        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getUsername()).isEqualTo("testuser");
        
        verify(userJpaRepository).findById(userId);
        verify(userMapper).entityToDomain(entity);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        // Given
        UUID userId = UUID.randomUUID();
        when(userJpaRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        // When & Then
        assertThatThrownBy(() -> userRepository.findById(userId))
                .isInstanceOf(NoSuchElementException.class);
        
        verify(userJpaRepository).findById(userId);
        verify(userMapper, never()).entityToDomain(any());
    }

    @Test
    void shouldReturnTrueWhenEmailExists() {
        // Given
        String email = "test@email.com";
        when(userJpaRepository.existsByEmail(email)).thenReturn(true);

        // When
        boolean result = userRepository.existsByEmail(email);

        // Then
        assertThat(result).isTrue();
        verify(userJpaRepository).existsByEmail(email);
    }

    @Test
    void shouldReturnFalseWhenEmailDoesNotExist() {
        // Given
        String email = "test@email.com";
        when(userJpaRepository.existsByEmail(email)).thenReturn(false);

        // When
        boolean result = userRepository.existsByEmail(email);

        // Then
        assertThat(result).isFalse();
        verify(userJpaRepository).existsByEmail(email);
    }

    @Test
    void shouldReturnTrueWhenUsernameExists() {
        // Given
        String username = "testuser";
        when(userJpaRepository.existsByUsername(username)).thenReturn(true);

        // When
        boolean result = userRepository.existsByUsername(username);

        // Then
        assertThat(result).isTrue();
        verify(userJpaRepository).existsByUsername(username);
    }

    @Test
    void shouldReturnFalseWhenUsernameDoesNotExist() {
        // Given
        String username = "testuser";
        when(userJpaRepository.existsByUsername(username)).thenReturn(false);

        // When
        boolean result = userRepository.existsByUsername(username);

        // Then
        assertThat(result).isFalse();
        verify(userJpaRepository).existsByUsername(username);
    }
} 