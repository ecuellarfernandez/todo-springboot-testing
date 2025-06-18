package com.todoapp.task.adapter.out;

import com.todoapp.task.domain.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class TaskRepositoryImplTest {

    private TaskRepositoryImpl taskRepository;

    @BeforeEach
    void setUp() {
        taskRepository = new TaskRepositoryImpl();
    }

    @Test
    void shouldSaveTaskSuccessfully() {
        // Given
        UUID todoListId = UUID.randomUUID();
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);
        Task task = new Task(null, "Test Task", "Test Description", false, dueDate, todoListId);

        // When
        Task savedTask = taskRepository.save(task);

        // Then
        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getId()).isNotNull();
        assertThat(savedTask.getTitle()).isEqualTo("Test Task");
        assertThat(savedTask.getDescription()).isEqualTo("Test Description");
        assertThat(savedTask.isCompleted()).isFalse();
        assertThat(savedTask.getDueDate()).isEqualTo(dueDate);
        assertThat(savedTask.getTodoListId()).isEqualTo(todoListId);
    }

    @Test
    void shouldSaveTaskWithExistingId() {
        // Given
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);
        Task task = new Task(taskId, "Test Task", "Test Description", false, dueDate, todoListId);

        // When
        Task savedTask = taskRepository.save(task);

        // Then
        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getId()).isEqualTo(taskId);
        assertThat(savedTask.getTitle()).isEqualTo("Test Task");
    }

    @Test
    void shouldFindTaskByIdSuccessfully() {
        // Given
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);
        Task task = new Task(taskId, "Test Task", "Test Description", false, dueDate, todoListId);
        taskRepository.save(task);

        // When
        Task foundTask = taskRepository.findById(taskId);

        // Then
        assertThat(foundTask).isNotNull();
        assertThat(foundTask.getId()).isEqualTo(taskId);
        assertThat(foundTask.getTitle()).isEqualTo("Test Task");
        assertThat(foundTask.getDescription()).isEqualTo("Test Description");
        assertThat(foundTask.isCompleted()).isFalse();
        assertThat(foundTask.getDueDate()).isEqualTo(dueDate);
        assertThat(foundTask.getTodoListId()).isEqualTo(todoListId);
    }

    @Test
    void shouldReturnNullWhenTaskNotFound() {
        // Given
        UUID nonExistentId = UUID.randomUUID();

        // When
        Task foundTask = taskRepository.findById(nonExistentId);

        // Then
        assertThat(foundTask).isNull();
    }

    @Test
    void shouldFindTasksByTodoListIdSuccessfully() {
        // Given
        UUID todoListId1 = UUID.randomUUID();
        UUID todoListId2 = UUID.randomUUID();
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);
        
        Task task1 = new Task(null, "Task 1", "Description 1", false, dueDate, todoListId1);
        Task task2 = new Task(null, "Task 2", "Description 2", true, dueDate, todoListId1);
        Task task3 = new Task(null, "Task 3", "Description 3", false, dueDate, todoListId2);
        
        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);

        // When
        List<Task> tasksForTodoList1 = taskRepository.findByTodoListId(todoListId1);

        // Then
        assertThat(tasksForTodoList1).hasSize(2);
        assertThat(tasksForTodoList1).extracting("title").containsExactlyInAnyOrder("Task 1", "Task 2");
        assertThat(tasksForTodoList1).extracting("todoListId").allMatch(id -> id.equals(todoListId1));
    }

    @Test
    void shouldReturnEmptyListWhenNoTasksForTodoListId() {
        // Given
        UUID nonExistentTodoListId = UUID.randomUUID();

        // When
        List<Task> tasks = taskRepository.findByTodoListId(nonExistentTodoListId);

        // Then
        assertThat(tasks).isEmpty();
    }

    @Test
    void shouldDeleteTaskSuccessfully() {
        // Given
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);
        Task task = new Task(taskId, "Test Task", "Test Description", false, dueDate, todoListId);
        taskRepository.save(task);

        // Verify task exists
        assertThat(taskRepository.findById(taskId)).isNotNull();

        // When
        taskRepository.delete(taskId);

        // Then
        assertThat(taskRepository.findById(taskId)).isNull();
        assertThat(taskRepository.existsById(taskId)).isFalse();
    }

    @Test
    void shouldDeleteNonExistentTaskWithoutError() {
        // Given
        UUID nonExistentId = UUID.randomUUID();

        // When & Then
        assertThatNoException().isThrownBy(() -> taskRepository.delete(nonExistentId));
    }

    @Test
    void shouldReturnTrueWhenTaskExists() {
        // Given
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);
        Task task = new Task(taskId, "Test Task", "Test Description", false, dueDate, todoListId);
        taskRepository.save(task);

        // When
        boolean exists = taskRepository.existsById(taskId);

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    void shouldReturnFalseWhenTaskDoesNotExist() {
        // Given
        UUID nonExistentId = UUID.randomUUID();

        // When
        boolean exists = taskRepository.existsById(nonExistentId);

        // Then
        assertThat(exists).isFalse();
    }

    @Test
    void shouldUpdateExistingTask() {
        // Given
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);
        Task originalTask = new Task(taskId, "Original Task", "Original Description", false, dueDate, todoListId);
        taskRepository.save(originalTask);

        // When
        Task updatedTask = new Task(taskId, "Updated Task", "Updated Description", true, dueDate, todoListId);
        Task savedTask = taskRepository.save(updatedTask);

        // Then
        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getId()).isEqualTo(taskId);
        assertThat(savedTask.getTitle()).isEqualTo("Updated Task");
        assertThat(savedTask.getDescription()).isEqualTo("Updated Description");
        assertThat(savedTask.isCompleted()).isTrue();
        
        // Verify the task was actually updated in the repository
        Task foundTask = taskRepository.findById(taskId);
        assertThat(foundTask.getTitle()).isEqualTo("Updated Task");
        assertThat(foundTask.getDescription()).isEqualTo("Updated Description");
        assertThat(foundTask.isCompleted()).isTrue();
    }

    @Test
    void shouldHandleMultipleTasksWithSameTodoListId() {
        // Given
        UUID todoListId = UUID.randomUUID();
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);
        
        Task task1 = new Task(null, "Task 1", "Description 1", false, dueDate, todoListId);
        Task task2 = new Task(null, "Task 2", "Description 2", true, dueDate, todoListId);
        Task task3 = new Task(null, "Task 3", "Description 3", false, dueDate, todoListId);
        
        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);

        // When
        List<Task> tasks = taskRepository.findByTodoListId(todoListId);

        // Then
        assertThat(tasks).hasSize(3);
        assertThat(tasks).extracting("title").containsExactlyInAnyOrder("Task 1", "Task 2", "Task 3");
        assertThat(tasks).extracting("completed").containsExactlyInAnyOrder(false, true, false);
    }

    @Test
    void shouldHandleTaskWithNullValues() {
        // Given
        UUID todoListId = UUID.randomUUID();
        Task task = new Task(null, null, null, false, null, todoListId);

        // When
        Task savedTask = taskRepository.save(task);

        // Then
        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getId()).isNotNull();
        assertThat(savedTask.getTitle()).isNull();
        assertThat(savedTask.getDescription()).isNull();
        assertThat(savedTask.getDueDate()).isNull();
        assertThat(savedTask.getTodoListId()).isEqualTo(todoListId);
        assertThat(savedTask.isCompleted()).isFalse();
    }

    @Test
    void shouldHandleTaskWithEmptyStrings() {
        // Given
        UUID todoListId = UUID.randomUUID();
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);
        Task task = new Task(null, "", "", false, dueDate, todoListId);

        // When
        Task savedTask = taskRepository.save(task);

        // Then
        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getId()).isNotNull();
        assertThat(savedTask.getTitle()).isEmpty();
        assertThat(savedTask.getDescription()).isEmpty();
        assertThat(savedTask.getDueDate()).isEqualTo(dueDate);
        assertThat(savedTask.getTodoListId()).isEqualTo(todoListId);
        assertThat(savedTask.isCompleted()).isFalse();
    }
} 