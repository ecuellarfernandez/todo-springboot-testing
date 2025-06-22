package com.todoapp.user.dto;

import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class UserResponseDTOTest {

    @Test
    void shouldCreateValidUserResponseDTO() {
        UUID userId = UUID.randomUUID();
        UserResponseDTO dto = new UserResponseDTO(userId, "testuser", "test@email.com");
        
        assertThat(dto.id()).isEqualTo(userId);
        assertThat(dto.username()).isEqualTo("testuser");
        assertThat(dto.email()).isEqualTo("test@email.com");
    }

    @Test
    void shouldBeImmutable() {
        UUID userId = UUID.randomUUID();
        UserResponseDTO dto1 = new UserResponseDTO(userId, "testuser", "test@email.com");
        UserResponseDTO dto2 = new UserResponseDTO(userId, "testuser", "test@email.com");
        
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    void shouldNotBeEqualToDifferentDTO() {
        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();
        UserResponseDTO dto1 = new UserResponseDTO(userId1, "testuser", "test@email.com");
        UserResponseDTO dto2 = new UserResponseDTO(userId2, "testuser", "test@email.com");
        
        assertThat(dto1).isNotEqualTo(dto2);
        assertThat(dto1.hashCode()).isNotEqualTo(dto2.hashCode());
    }

    @Test
    void shouldHandleNullValues() {
        UserResponseDTO dto = new UserResponseDTO(null, null, null);
        
        assertThat(dto.id()).isNull();
        assertThat(dto.username()).isNull();
        assertThat(dto.email()).isNull();
    }

    @Test
    void shouldHaveConsistentToString() {
        UUID userId = UUID.randomUUID();
        UserResponseDTO dto = new UserResponseDTO(userId, "testuser", "test@email.com");
        
        String toString = dto.toString();
        assertThat(toString).contains(userId.toString());
        assertThat(toString).contains("testuser");
        assertThat(toString).contains("test@email.com");
    }

    @Test
    void shouldHandleEmptyStrings() {
        UUID userId = UUID.randomUUID();
        UserResponseDTO dto = new UserResponseDTO(userId, "", "");
        
        assertThat(dto.id()).isEqualTo(userId);
        assertThat(dto.username()).isEmpty();
        assertThat(dto.email()).isEmpty();
    }
} 