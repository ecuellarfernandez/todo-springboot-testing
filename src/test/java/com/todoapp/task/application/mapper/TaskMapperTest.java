package com.todoapp.task.application.mapper;

import com.todoapp.task.domain.Task;
import com.todoapp.task.dto.TaskResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class TaskMapperTest {

    private TaskMapper taskMapper;

    @BeforeEach
    void setUp() {
        taskMapper = new TaskMapper();
    }

    @Test
    void shouldMapTaskToResponseDTO() {
        // Given
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "Test Task";
        String description = "Test Description";
        boolean completed = false;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);
        
        Task task = new Task(taskId, title, description, completed, dueDate, todoListId);

        // When
        TaskResponseDTO result = taskMapper.toResponseDTO(task);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(taskId);
        assertThat(result.title()).isEqualTo(title);
        assertThat(result.description()).isEqualTo(description);
        assertThat(result.completed()).isEqualTo(completed);
        assertThat(result.dueDate()).isEqualTo(dueDate);
        assertThat(result.todoListId()).isEqualTo(todoListId);
    }

    @Test
    void shouldMapTaskWithNullId() {
        // Given
        UUID todoListId = UUID.randomUUID();
        String title = "Test Task";
        String description = "Test Description";
        boolean completed = true;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);
        
        Task task = new Task(null, title, description, completed, dueDate, todoListId);

        // When
        TaskResponseDTO result = taskMapper.toResponseDTO(task);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isNull();
        assertThat(result.title()).isEqualTo(title);
        assertThat(result.description()).isEqualTo(description);
        assertThat(result.completed()).isEqualTo(completed);
        assertThat(result.dueDate()).isEqualTo(dueDate);
        assertThat(result.todoListId()).isEqualTo(todoListId);
    }

    @Test
    void shouldMapTaskWithNullTitle() {
        // Given
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String description = "Test Description";
        boolean completed = false;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);
        
        Task task = new Task(taskId, null, description, completed, dueDate, todoListId);

        // When
        TaskResponseDTO result = taskMapper.toResponseDTO(task);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(taskId);
        assertThat(result.title()).isNull();
        assertThat(result.description()).isEqualTo(description);
        assertThat(result.completed()).isEqualTo(completed);
        assertThat(result.dueDate()).isEqualTo(dueDate);
        assertThat(result.todoListId()).isEqualTo(todoListId);
    }

    @Test
    void shouldMapTaskWithNullDescription() {
        // Given
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "Test Task";
        boolean completed = true;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);
        
        Task task = new Task(taskId, title, null, completed, dueDate, todoListId);

        // When
        TaskResponseDTO result = taskMapper.toResponseDTO(task);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(taskId);
        assertThat(result.title()).isEqualTo(title);
        assertThat(result.description()).isNull();
        assertThat(result.completed()).isEqualTo(completed);
        assertThat(result.dueDate()).isEqualTo(dueDate);
        assertThat(result.todoListId()).isEqualTo(todoListId);
    }

    @Test
    void shouldMapTaskWithNullDueDate() {
        // Given
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "Test Task";
        String description = "Test Description";
        boolean completed = false;
        
        Task task = new Task(taskId, title, description, completed, null, todoListId);

        // When
        TaskResponseDTO result = taskMapper.toResponseDTO(task);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(taskId);
        assertThat(result.title()).isEqualTo(title);
        assertThat(result.description()).isEqualTo(description);
        assertThat(result.completed()).isEqualTo(completed);
        assertThat(result.dueDate()).isNull();
        assertThat(result.todoListId()).isEqualTo(todoListId);
    }

    @Test
    void shouldMapTaskWithNullTodoListId() {
        // Given
        UUID taskId = UUID.randomUUID();
        String title = "Test Task";
        String description = "Test Description";
        boolean completed = true;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);
        
        Task task = new Task(taskId, title, description, completed, dueDate, null);

        // When
        TaskResponseDTO result = taskMapper.toResponseDTO(task);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(taskId);
        assertThat(result.title()).isEqualTo(title);
        assertThat(result.description()).isEqualTo(description);
        assertThat(result.completed()).isEqualTo(completed);
        assertThat(result.dueDate()).isEqualTo(dueDate);
        assertThat(result.todoListId()).isNull();
    }

    @Test
    void shouldMapTaskWithEmptyStrings() {
        // Given
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "";
        String description = "";
        boolean completed = false;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);
        
        Task task = new Task(taskId, title, description, completed, dueDate, todoListId);

        // When
        TaskResponseDTO result = taskMapper.toResponseDTO(task);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(taskId);
        assertThat(result.title()).isEmpty();
        assertThat(result.description()).isEmpty();
        assertThat(result.completed()).isEqualTo(completed);
        assertThat(result.dueDate()).isEqualTo(dueDate);
        assertThat(result.todoListId()).isEqualTo(todoListId);
    }

    @Test
    void shouldMapTaskWithSpecialCharacters() {
        // Given
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea con caracteres especiales: áéíóú ñ @#$%^&*()";
        String description = "Descripción con símbolos: ©®™ €£¥ ¢∞§¶•ªº";
        boolean completed = true;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);
        
        Task task = new Task(taskId, title, description, completed, dueDate, todoListId);

        // When
        TaskResponseDTO result = taskMapper.toResponseDTO(task);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(taskId);
        assertThat(result.title()).isEqualTo(title);
        assertThat(result.description()).isEqualTo(description);
        assertThat(result.completed()).isEqualTo(completed);
        assertThat(result.dueDate()).isEqualTo(dueDate);
        assertThat(result.todoListId()).isEqualTo(todoListId);
    }

    @Test
    void shouldMapTaskWithUnicodeCharacters() {
        // Given
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea con emojis 🚀📝✅";
        String description = "Descripción con unicode: 🌟✨💫";
        boolean completed = false;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);
        
        Task task = new Task(taskId, title, description, completed, dueDate, todoListId);

        // When
        TaskResponseDTO result = taskMapper.toResponseDTO(task);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(taskId);
        assertThat(result.title()).isEqualTo(title);
        assertThat(result.description()).isEqualTo(description);
        assertThat(result.completed()).isEqualTo(completed);
        assertThat(result.dueDate()).isEqualTo(dueDate);
        assertThat(result.todoListId()).isEqualTo(todoListId);
    }

    @Test
    void shouldMapTaskWithLongStrings() {
        // Given
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "A".repeat(1000);
        String description = "B".repeat(2000);
        boolean completed = true;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);
        
        Task task = new Task(taskId, title, description, completed, dueDate, todoListId);

        // When
        TaskResponseDTO result = taskMapper.toResponseDTO(task);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(taskId);
        assertThat(result.title()).isEqualTo(title);
        assertThat(result.description()).isEqualTo(description);
        assertThat(result.completed()).isEqualTo(completed);
        assertThat(result.dueDate()).isEqualTo(dueDate);
        assertThat(result.todoListId()).isEqualTo(todoListId);
    }

    @Test
    void shouldMapTaskWithCompletedTrue() {
        // Given
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "Completed Task";
        String description = "This task is completed";
        boolean completed = true;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);
        
        Task task = new Task(taskId, title, description, completed, dueDate, todoListId);

        // When
        TaskResponseDTO result = taskMapper.toResponseDTO(task);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(taskId);
        assertThat(result.title()).isEqualTo(title);
        assertThat(result.description()).isEqualTo(description);
        assertThat(result.completed()).isTrue();
        assertThat(result.dueDate()).isEqualTo(dueDate);
        assertThat(result.todoListId()).isEqualTo(todoListId);
    }

    @Test
    void shouldMapTaskWithCompletedFalse() {
        // Given
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "Pending Task";
        String description = "This task is pending";
        boolean completed = false;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);
        
        Task task = new Task(taskId, title, description, completed, dueDate, todoListId);

        // When
        TaskResponseDTO result = taskMapper.toResponseDTO(task);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(taskId);
        assertThat(result.title()).isEqualTo(title);
        assertThat(result.description()).isEqualTo(description);
        assertThat(result.completed()).isFalse();
        assertThat(result.dueDate()).isEqualTo(dueDate);
        assertThat(result.todoListId()).isEqualTo(todoListId);
    }

    @Test
    void shouldMapTaskWithPastDueDate() {
        // Given
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "Past Task";
        String description = "This task is overdue";
        boolean completed = false;
        LocalDateTime dueDate = LocalDateTime.now().minusDays(5);
        
        Task task = new Task(taskId, title, description, completed, dueDate, todoListId);

        // When
        TaskResponseDTO result = taskMapper.toResponseDTO(task);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(taskId);
        assertThat(result.title()).isEqualTo(title);
        assertThat(result.description()).isEqualTo(description);
        assertThat(result.completed()).isEqualTo(completed);
        assertThat(result.dueDate()).isEqualTo(dueDate);
        assertThat(result.todoListId()).isEqualTo(todoListId);
    }

    @Test
    void shouldMapTaskWithFutureDueDate() {
        // Given
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "Future Task";
        String description = "This task is in the future";
        boolean completed = false;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(30);
        
        Task task = new Task(taskId, title, description, completed, dueDate, todoListId);

        // When
        TaskResponseDTO result = taskMapper.toResponseDTO(task);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(taskId);
        assertThat(result.title()).isEqualTo(title);
        assertThat(result.description()).isEqualTo(description);
        assertThat(result.completed()).isEqualTo(completed);
        assertThat(result.dueDate()).isEqualTo(dueDate);
        assertThat(result.todoListId()).isEqualTo(todoListId);
    }

    @Test
    void shouldMapTaskWithAllNullValues() {
        // Given
        Task task = new Task(null, null, null, false, null, null);

        // When
        TaskResponseDTO result = taskMapper.toResponseDTO(task);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isNull();
        assertThat(result.title()).isNull();
        assertThat(result.description()).isNull();
        assertThat(result.completed()).isFalse();
        assertThat(result.dueDate()).isNull();
        assertThat(result.todoListId()).isNull();
    }

    @Test
    void shouldMapTaskWithMinimalData() {
        // Given
        UUID taskId = UUID.randomUUID();
        String title = "Minimal Task";
        
        Task task = new Task(taskId, title, null, false, null, null);

        // When
        TaskResponseDTO result = taskMapper.toResponseDTO(task);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(taskId);
        assertThat(result.title()).isEqualTo(title);
        assertThat(result.description()).isNull();
        assertThat(result.completed()).isFalse();
        assertThat(result.dueDate()).isNull();
        assertThat(result.todoListId()).isNull();
    }

    @Test
    void shouldMapMultipleTasksConsistently() {
        // Given
        UUID taskId1 = UUID.randomUUID();
        UUID taskId2 = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        
        Task task1 = new Task(taskId1, "Task 1", "Description 1", false, LocalDateTime.now().plusDays(1), todoListId);
        Task task2 = new Task(taskId2, "Task 2", "Description 2", true, LocalDateTime.now().plusDays(2), todoListId);

        // When
        TaskResponseDTO result1 = taskMapper.toResponseDTO(task1);
        TaskResponseDTO result2 = taskMapper.toResponseDTO(task2);

        // Then
        assertThat(result1).isNotNull();
        assertThat(result1.id()).isEqualTo(taskId1);
        assertThat(result1.title()).isEqualTo("Task 1");
        assertThat(result1.completed()).isFalse();
        
        assertThat(result2).isNotNull();
        assertThat(result2.id()).isEqualTo(taskId2);
        assertThat(result2.title()).isEqualTo("Task 2");
        assertThat(result2.completed()).isTrue();
        
        // Verify both tasks have the same todoListId
        assertThat(result1.todoListId()).isEqualTo(todoListId);
        assertThat(result2.todoListId()).isEqualTo(todoListId);
    }
} 