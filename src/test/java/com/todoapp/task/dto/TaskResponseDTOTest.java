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
        int position = 3;
        TaskResponseDTO dto = new TaskResponseDTO(taskId, "Test Task", "Test Description", false, dueDate, todoListId, projectId, position);

        assertThat(dto.id()).isEqualTo(taskId);
        assertThat(dto.title()).isEqualTo("Test Task");
        assertThat(dto.description()).isEqualTo("Test Description");
        assertThat(dto.completed()).isEqualTo(false);
        assertThat(dto.dueDate()).isEqualTo(dueDate);
        assertThat(dto.todoListId()).isEqualTo(todoListId);
        assertThat(dto.projectId()).isEqualTo(projectId);
        assertThat(dto.position()).isEqualTo(position);
    }

    @Test
    void shouldBeImmutable() {
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        LocalDate dueDate = LocalDate.now().plusDays(7);
        int position = 3;

        TaskResponseDTO dto1 = new TaskResponseDTO(taskId, "Test Task", "Test Description", false, dueDate, todoListId, projectId, position);
        TaskResponseDTO dto2 = new TaskResponseDTO(taskId, "Test Task", "Test Description", false, dueDate, todoListId, projectId, position);

        assertThat(dto1).isEqualTo(dto2);
    }

    @Test
    void shouldHandleNullValues() {
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        int position = 3;

        TaskResponseDTO dto = new TaskResponseDTO(taskId, "Test Task", null, false, null, todoListId, projectId, position);

        assertThat(dto.id()).isEqualTo(taskId);
        assertThat(dto.title()).isEqualTo("Test Task");
        assertThat(dto.description()).isNull();
        assertThat(dto.completed()).isEqualTo(false);
        assertThat(dto.dueDate()).isNull();
        assertThat(dto.todoListId()).isEqualTo(todoListId);
        assertThat(dto.projectId()).isEqualTo(projectId);
        assertThat(dto.position()).isEqualTo(position);
    }

    @Test
    void shouldHandleCompletedTask() {
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        LocalDate dueDate = LocalDate.now().plusDays(7);
        int position = 3;

        TaskResponseDTO dto = new TaskResponseDTO(taskId, "Test Task", "Test Description", true, dueDate, todoListId, projectId, position);

        assertThat(dto.completed()).isEqualTo(false);
    }

    @Test
    void shouldHandleIncompleteTask() {
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        LocalDate dueDate = LocalDate.now().plusDays(7);
        int position = 3;

        TaskResponseDTO dto = new TaskResponseDTO(taskId, "Test Task", "Test Description", false, dueDate, todoListId, projectId, position);

        assertThat(dto.completed()).isEqualTo(false);
    }

    @Test
    void shouldHandleEmptyDescription() {
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        LocalDate dueDate = LocalDate.now().plusDays(7);
        int position = 3;

        TaskResponseDTO dto = new TaskResponseDTO(taskId, "Test Task", "", false, dueDate, todoListId, projectId, position);

        assertThat(dto.description()).isEqualTo("");
    }

    @Test
    void shouldHandlePastDueDate() {
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        LocalDate pastDate = LocalDate.now().minusDays(1);
        int position = 3;

        TaskResponseDTO dto = new TaskResponseDTO(taskId, "Test Task", "Test Description", false, pastDate, todoListId, projectId, position);

        assertThat(dto.dueDate()).isEqualTo(pastDate);
    }

    @Test
    void shouldHandleFutureDueDate() {
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        LocalDate futureDate = LocalDate.now().plusDays(30);
        int position = 3;

        TaskResponseDTO dto = new TaskResponseDTO(taskId, "Test Task", "Test Description", false, futureDate, todoListId, projectId, position);

        assertThat(dto.dueDate()).isEqualTo(futureDate);
    }

    @Test
    void shouldHandleTodayDueDate() {
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        LocalDate today = LocalDate.now();
        int position = 3;

        TaskResponseDTO dto = new TaskResponseDTO(taskId, "Test Task", "Test Description", false, today, todoListId, projectId, position);

        assertThat(dto.dueDate()).isEqualTo(today);
    }
}
