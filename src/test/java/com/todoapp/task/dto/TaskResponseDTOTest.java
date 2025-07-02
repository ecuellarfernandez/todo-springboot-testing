package com.todoapp.task.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import java.util.UUID;

class TaskResponseDTOTest {

    @Test
    void shouldStoreValues() {
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        LocalDate dueDate = LocalDate.now().plusDays(7);
        
        TaskResponseDTO dto = new TaskResponseDTO(taskId, "Test Task", "Test Description", false, dueDate, todoListId, projectId);
        
        assertThat(dto.id()).isEqualTo(taskId);
        assertThat(dto.title()).isEqualTo("Test Task");
        assertThat(dto.description()).isEqualTo("Test Description");
        assertThat(dto.completed()).isEqualTo(false);
        assertThat(dto.dueDate()).isEqualTo(dueDate);
        assertThat(dto.todoListId()).isEqualTo(todoListId);
        assertThat(dto.projectId()).isEqualTo(projectId);
    }

    @Test
    void shouldBeImmutable() {
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        LocalDate dueDate = LocalDate.now().plusDays(7);
        
        TaskResponseDTO dto1 = new TaskResponseDTO(taskId, "Test Task", "Test Description", false, dueDate, todoListId, projectId);
        TaskResponseDTO dto2 = new TaskResponseDTO(taskId, "Test Task", "Test Description", false, dueDate, todoListId, projectId);
        
        assertThat(dto1).isEqualTo(dto2);
    }

    @Test
    void shouldHandleNullValues() {
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        
        TaskResponseDTO dto = new TaskResponseDTO(taskId, "Test Task", null, false, null, todoListId, projectId);
        
        assertThat(dto.id()).isEqualTo(taskId);
        assertThat(dto.title()).isEqualTo("Test Task");
        assertThat(dto.description()).isNull();
        assertThat(dto.completed()).isEqualTo(false);
        assertThat(dto.dueDate()).isNull();
        assertThat(dto.todoListId()).isEqualTo(todoListId);
        assertThat(dto.projectId()).isEqualTo(projectId);
    }

    @Test
    void shouldHandleCompletedTask() {
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        LocalDate dueDate = LocalDate.now().plusDays(7);
        
        TaskResponseDTO dto = new TaskResponseDTO(taskId, "Test Task", "Test Description", true, dueDate, todoListId, projectId);
        
        assertThat(dto.completed()).isEqualTo(false);
    }

    @Test
    void shouldHandleIncompleteTask() {
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        LocalDate dueDate = LocalDate.now().plusDays(7);
        
        TaskResponseDTO dto = new TaskResponseDTO(taskId, "Test Task", "Test Description", false, dueDate, todoListId, projectId);
        
        assertThat(dto.completed()).isEqualTo(false);
    }

    @Test
    void shouldHandleEmptyDescription() {
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        LocalDate dueDate = LocalDate.now().plusDays(7);
        
        TaskResponseDTO dto = new TaskResponseDTO(taskId, "Test Task", "", false, dueDate, todoListId, projectId);
        
        assertThat(dto.description()).isEqualTo("");
    }

    @Test
    void shouldHandlePastDueDate() {
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        LocalDate pastDate = LocalDate.now().minusDays(1);
        
        TaskResponseDTO dto = new TaskResponseDTO(taskId, "Test Task", "Test Description", false, pastDate, todoListId, projectId);
        
        assertThat(dto.dueDate()).isEqualTo(pastDate);
    }

    @Test
    void shouldHandleFutureDueDate() {
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        LocalDate futureDate = LocalDate.now().plusDays(30);
        
        TaskResponseDTO dto = new TaskResponseDTO(taskId, "Test Task", "Test Description", false, futureDate, todoListId, projectId);
        
        assertThat(dto.dueDate()).isEqualTo(futureDate);
    }

    @Test
    void shouldHandleTodayDueDate() {
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        LocalDate today = LocalDate.now();
        
        TaskResponseDTO dto = new TaskResponseDTO(taskId, "Test Task", "Test Description", false, today, todoListId, projectId);
        
        assertThat(dto.dueDate()).isEqualTo(today);
    }
} 