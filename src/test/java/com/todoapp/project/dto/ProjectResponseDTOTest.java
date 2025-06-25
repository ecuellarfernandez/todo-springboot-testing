package com.todoapp.project.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.UUID;

class ProjectResponseDTOTest {

    @Test
    void shouldStoreValues() {
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();
        
        ProjectResponseDTO dto = new ProjectResponseDTO(id, "Test Project", "Test Description", userId, createdAt);
        
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.name()).isEqualTo("Test Project");
        assertThat(dto.description()).isEqualTo("Test Description");
        assertThat(dto.userId()).isEqualTo(userId);
        assertThat(dto.createdAt()).isEqualTo(createdAt);
    }

    @Test
    void shouldBeImmutable() {
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();
        
        ProjectResponseDTO dto1 = new ProjectResponseDTO(id, "Project A", "Description A", userId, createdAt);
        ProjectResponseDTO dto2 = new ProjectResponseDTO(id, "Project A", "Description A", userId, createdAt);
        
        assertThat(dto1).isEqualTo(dto2);
    }

    @Test
    void shouldHandleNullDescription() {
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();
        
        ProjectResponseDTO dto = new ProjectResponseDTO(id, "Test Project", null, userId, createdAt);
        
        assertThat(dto.name()).isEqualTo("Test Project");
        assertThat(dto.description()).isNull();
    }

    @Test
    void shouldHaveConsistentHashCode() {
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();
        
        ProjectResponseDTO dto1 = new ProjectResponseDTO(id, "Project A", "Description A", userId, createdAt);
        ProjectResponseDTO dto2 = new ProjectResponseDTO(id, "Project A", "Description A", userId, createdAt);
        
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    void shouldHaveMeaningfulToString() {
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();
        
        ProjectResponseDTO dto = new ProjectResponseDTO(id, "Test Project", "Test Description", userId, createdAt);
        String toString = dto.toString();
        
        assertThat(toString).contains("Test Project");
        assertThat(toString).contains("Test Description");
        assertThat(toString).contains(id.toString());
        assertThat(toString).contains(userId.toString());
    }
} 