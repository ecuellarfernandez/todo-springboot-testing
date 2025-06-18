package com.todoapp.task.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class TaskTest {

    @Test
    void shouldCreateTaskWithConstructor() {
        // Given
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "Test Task";
        String description = "Test Description";
        boolean completed = false;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);

        // When
        Task task = new Task(taskId, title, description, completed, dueDate, todoListId);

        // Then
        assertThat(task).isNotNull();
        assertThat(task.getId()).isEqualTo(taskId);
        assertThat(task.getTitle()).isEqualTo(title);
        assertThat(task.getDescription()).isEqualTo(description);
        assertThat(task.isCompleted()).isEqualTo(completed);
        assertThat(task.getDueDate()).isEqualTo(dueDate);
        assertThat(task.getTodoListId()).isEqualTo(todoListId);
    }

    @Test
    void shouldCreateTaskWithNullId() {
        // Given
        UUID todoListId = UUID.randomUUID();
        String title = "Test Task";
        String description = "Test Description";
        boolean completed = true;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);

        // When
        Task task = new Task(null, title, description, completed, dueDate, todoListId);

        // Then
        assertThat(task).isNotNull();
        assertThat(task.getId()).isNull();
        assertThat(task.getTitle()).isEqualTo(title);
        assertThat(task.getDescription()).isEqualTo(description);
        assertThat(task.isCompleted()).isEqualTo(completed);
        assertThat(task.getDueDate()).isEqualTo(dueDate);
        assertThat(task.getTodoListId()).isEqualTo(todoListId);
    }

    @Test
    void shouldCreateTaskWithNullTitle() {
        // Given
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String description = "Test Description";
        boolean completed = false;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);

        // When
        Task task = new Task(taskId, null, description, completed, dueDate, todoListId);

        // Then
        assertThat(task).isNotNull();
        assertThat(task.getId()).isEqualTo(taskId);
        assertThat(task.getTitle()).isNull();
        assertThat(task.getDescription()).isEqualTo(description);
        assertThat(task.isCompleted()).isEqualTo(completed);
        assertThat(task.getDueDate()).isEqualTo(dueDate);
        assertThat(task.getTodoListId()).isEqualTo(todoListId);
    }

    @Test
    void shouldCreateTaskWithNullDescription() {
        // Given
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "Test Task";
        boolean completed = true;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);

        // When
        Task task = new Task(taskId, title, null, completed, dueDate, todoListId);

        // Then
        assertThat(task).isNotNull();
        assertThat(task.getId()).isEqualTo(taskId);
        assertThat(task.getTitle()).isEqualTo(title);
        assertThat(task.getDescription()).isNull();
        assertThat(task.isCompleted()).isEqualTo(completed);
        assertThat(task.getDueDate()).isEqualTo(dueDate);
        assertThat(task.getTodoListId()).isEqualTo(todoListId);
    }

    @Test
    void shouldCreateTaskWithNullDueDate() {
        // Given
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "Test Task";
        String description = "Test Description";
        boolean completed = false;

        // When
        Task task = new Task(taskId, title, description, completed, null, todoListId);

        // Then
        assertThat(task).isNotNull();
        assertThat(task.getId()).isEqualTo(taskId);
        assertThat(task.getTitle()).isEqualTo(title);
        assertThat(task.getDescription()).isEqualTo(description);
        assertThat(task.isCompleted()).isEqualTo(completed);
        assertThat(task.getDueDate()).isNull();
        assertThat(task.getTodoListId()).isEqualTo(todoListId);
    }

    @Test
    void shouldCreateTaskWithNullTodoListId() {
        // Given
        UUID taskId = UUID.randomUUID();
        String title = "Test Task";
        String description = "Test Description";
        boolean completed = true;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);

        // When
        Task task = new Task(taskId, title, description, completed, dueDate, null);

        // Then
        assertThat(task).isNotNull();
        assertThat(task.getId()).isEqualTo(taskId);
        assertThat(task.getTitle()).isEqualTo(title);
        assertThat(task.getDescription()).isEqualTo(description);
        assertThat(task.isCompleted()).isEqualTo(completed);
        assertThat(task.getDueDate()).isEqualTo(dueDate);
        assertThat(task.getTodoListId()).isNull();
    }

    @Test
    void shouldSetAndGetId() {
        // Given
        Task task = new Task(null, "Test Task", "Test Description", false, LocalDateTime.now().plusDays(1), UUID.randomUUID());
        UUID newId = UUID.randomUUID();

        // When
        task.setId(newId);

        // Then
        assertThat(task.getId()).isEqualTo(newId);
    }

    @Test
    void shouldSetAndGetTitle() {
        // Given
        Task task = new Task(UUID.randomUUID(), "Original Title", "Test Description", false, LocalDateTime.now().plusDays(1), UUID.randomUUID());
        String newTitle = "Updated Title";

        // When
        task.setTitle(newTitle);

        // Then
        assertThat(task.getTitle()).isEqualTo(newTitle);
    }

    @Test
    void shouldSetAndGetDescription() {
        // Given
        Task task = new Task(UUID.randomUUID(), "Test Task", "Original Description", false, LocalDateTime.now().plusDays(1), UUID.randomUUID());
        String newDescription = "Updated Description";

        // When
        task.setDescription(newDescription);

        // Then
        assertThat(task.getDescription()).isEqualTo(newDescription);
    }

    @Test
    void shouldSetAndGetCompleted() {
        // Given
        Task task = new Task(UUID.randomUUID(), "Test Task", "Test Description", false, LocalDateTime.now().plusDays(1), UUID.randomUUID());

        // When - Set to true
        task.setCompleted(true);

        // Then
        assertThat(task.isCompleted()).isTrue();

        // When - Set to false
        task.setCompleted(false);

        // Then
        assertThat(task.isCompleted()).isFalse();
    }

    @Test
    void shouldSetAndGetDueDate() {
        // Given
        Task task = new Task(UUID.randomUUID(), "Test Task", "Test Description", false, LocalDateTime.now().plusDays(1), UUID.randomUUID());
        LocalDateTime newDueDate = LocalDateTime.now().plusDays(5);

        // When
        task.setDueDate(newDueDate);

        // Then
        assertThat(task.getDueDate()).isEqualTo(newDueDate);
    }

    @Test
    void shouldSetAndGetTodoListId() {
        // Given
        Task task = new Task(UUID.randomUUID(), "Test Task", "Test Description", false, LocalDateTime.now().plusDays(1), UUID.randomUUID());
        UUID newTodoListId = UUID.randomUUID();

        // When
        task.setTodoListId(newTodoListId);

        // Then
        assertThat(task.getTodoListId()).isEqualTo(newTodoListId);
    }

    @Test
    void shouldHandleNullValuesInSetters() {
        // Given
        Task task = new Task(UUID.randomUUID(), "Test Task", "Test Description", false, LocalDateTime.now().plusDays(1), UUID.randomUUID());

        // When
        task.setId(null);
        task.setTitle(null);
        task.setDescription(null);
        task.setDueDate(null);
        task.setTodoListId(null);

        // Then
        assertThat(task.getId()).isNull();
        assertThat(task.getTitle()).isNull();
        assertThat(task.getDescription()).isNull();
        assertThat(task.getDueDate()).isNull();
        assertThat(task.getTodoListId()).isNull();
    }

    @Test
    void shouldHandleEmptyStrings() {
        // Given
        Task task = new Task(UUID.randomUUID(), "Test Task", "Test Description", false, LocalDateTime.now().plusDays(1), UUID.randomUUID());

        // When
        task.setTitle("");
        task.setDescription("");

        // Then
        assertThat(task.getTitle()).isEmpty();
        assertThat(task.getDescription()).isEmpty();
    }

    @Test
    void shouldHandleSpecialCharacters() {
        // Given
        Task task = new Task(UUID.randomUUID(), "Test Task", "Test Description", false, LocalDateTime.now().plusDays(1), UUID.randomUUID());
        String titleWithSpecialChars = "Tarea con caracteres especiales: áéíóú ñ @#$%^&*()";
        String descriptionWithSpecialChars = "Descripción con símbolos: ©®™ €£¥ ¢∞§¶•ªº";

        // When
        task.setTitle(titleWithSpecialChars);
        task.setDescription(descriptionWithSpecialChars);

        // Then
        assertThat(task.getTitle()).isEqualTo(titleWithSpecialChars);
        assertThat(task.getDescription()).isEqualTo(descriptionWithSpecialChars);
    }

    @Test
    void shouldHandleUnicodeCharacters() {
        // Given
        Task task = new Task(UUID.randomUUID(), "Test Task", "Test Description", false, LocalDateTime.now().plusDays(1), UUID.randomUUID());
        String titleWithUnicode = "Tarea con emojis 🚀📝✅";
        String descriptionWithUnicode = "Descripción con unicode: 🌟✨💫";

        // When
        task.setTitle(titleWithUnicode);
        task.setDescription(descriptionWithUnicode);

        // Then
        assertThat(task.getTitle()).isEqualTo(titleWithUnicode);
        assertThat(task.getDescription()).isEqualTo(descriptionWithUnicode);
    }

    @Test
    void shouldHandleLongStrings() {
        // Given
        Task task = new Task(UUID.randomUUID(), "Test Task", "Test Description", false, LocalDateTime.now().plusDays(1), UUID.randomUUID());
        String longTitle = "A".repeat(1000);
        String longDescription = "B".repeat(2000);

        // When
        task.setTitle(longTitle);
        task.setDescription(longDescription);

        // Then
        assertThat(task.getTitle()).isEqualTo(longTitle);
        assertThat(task.getDescription()).isEqualTo(longDescription);
    }

    @Test
    void shouldHandleDifferentDueDates() {
        // Given
        Task task = new Task(UUID.randomUUID(), "Test Task", "Test Description", false, LocalDateTime.now().plusDays(1), UUID.randomUUID());
        LocalDateTime pastDate = LocalDateTime.now().minusDays(10);
        LocalDateTime futureDate = LocalDateTime.now().plusDays(30);
        LocalDateTime today = LocalDateTime.now();

        // When & Then - Past date
        task.setDueDate(pastDate);
        assertThat(task.getDueDate()).isEqualTo(pastDate);

        // When & Then - Future date
        task.setDueDate(futureDate);
        assertThat(task.getDueDate()).isEqualTo(futureDate);

        // When & Then - Today
        task.setDueDate(today);
        assertThat(task.getDueDate()).isEqualTo(today);
    }

    @Test
    void shouldHandleCompletedStatusChanges() {
        // Given
        Task task = new Task(UUID.randomUUID(), "Test Task", "Test Description", false, LocalDateTime.now().plusDays(1), UUID.randomUUID());

        // When - Initially false
        assertThat(task.isCompleted()).isFalse();

        // When - Set to true
        task.setCompleted(true);
        assertThat(task.isCompleted()).isTrue();

        // When - Set back to false
        task.setCompleted(false);
        assertThat(task.isCompleted()).isFalse();

        // When - Set to true again
        task.setCompleted(true);
        assertThat(task.isCompleted()).isTrue();
    }

    @Test
    void shouldHandleMultipleIdChanges() {
        // Given
        Task task = new Task(UUID.randomUUID(), "Test Task", "Test Description", false, LocalDateTime.now().plusDays(1), UUID.randomUUID());
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        UUID id3 = UUID.randomUUID();

        // When & Then
        task.setId(id1);
        assertThat(task.getId()).isEqualTo(id1);

        task.setId(id2);
        assertThat(task.getId()).isEqualTo(id2);
        assertThat(task.getId()).isNotEqualTo(id1);

        task.setId(id3);
        assertThat(task.getId()).isEqualTo(id3);
        assertThat(task.getId()).isNotEqualTo(id1);
        assertThat(task.getId()).isNotEqualTo(id2);
    }

    @Test
    void shouldHandleMultipleTodoListIdChanges() {
        // Given
        Task task = new Task(UUID.randomUUID(), "Test Task", "Test Description", false, LocalDateTime.now().plusDays(1), UUID.randomUUID());
        UUID todoListId1 = UUID.randomUUID();
        UUID todoListId2 = UUID.randomUUID();
        UUID todoListId3 = UUID.randomUUID();

        // When & Then
        task.setTodoListId(todoListId1);
        assertThat(task.getTodoListId()).isEqualTo(todoListId1);

        task.setTodoListId(todoListId2);
        assertThat(task.getTodoListId()).isEqualTo(todoListId2);
        assertThat(task.getTodoListId()).isNotEqualTo(todoListId1);

        task.setTodoListId(todoListId3);
        assertThat(task.getTodoListId()).isEqualTo(todoListId3);
        assertThat(task.getTodoListId()).isNotEqualTo(todoListId1);
        assertThat(task.getTodoListId()).isNotEqualTo(todoListId2);
    }

    @Test
    void shouldHandleCompleteTaskLifecycle() {
        // Given
        UUID taskId = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "Complete Task";
        String description = "Complete Description";
        boolean completed = false;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);

        // When - Create task
        Task task = new Task(taskId, title, description, completed, dueDate, todoListId);

        // Then - Verify initial state
        assertThat(task.getId()).isEqualTo(taskId);
        assertThat(task.getTitle()).isEqualTo(title);
        assertThat(task.getDescription()).isEqualTo(description);
        assertThat(task.isCompleted()).isEqualTo(completed);
        assertThat(task.getDueDate()).isEqualTo(dueDate);
        assertThat(task.getTodoListId()).isEqualTo(todoListId);

        // When - Update task properties
        String newTitle = "Updated Task";
        String newDescription = "Updated Description";
        boolean newCompleted = true;
        LocalDateTime newDueDate = LocalDateTime.now().plusDays(2);
        UUID newTodoListId = UUID.randomUUID();

        task.setTitle(newTitle);
        task.setDescription(newDescription);
        task.setCompleted(newCompleted);
        task.setDueDate(newDueDate);
        task.setTodoListId(newTodoListId);

        // Then - Verify updated state
        assertThat(task.getTitle()).isEqualTo(newTitle);
        assertThat(task.getDescription()).isEqualTo(newDescription);
        assertThat(task.isCompleted()).isEqualTo(newCompleted);
        assertThat(task.getDueDate()).isEqualTo(newDueDate);
        assertThat(task.getTodoListId()).isEqualTo(newTodoListId);
        
        // Verify ID remains unchanged
        assertThat(task.getId()).isEqualTo(taskId);
    }

    @Test
    void shouldHandleTaskWithAllNullValues() {
        // Given
        Task task = new Task(null, null, null, false, null, null);

        // Then
        assertThat(task).isNotNull();
        assertThat(task.getId()).isNull();
        assertThat(task.getTitle()).isNull();
        assertThat(task.getDescription()).isNull();
        assertThat(task.isCompleted()).isFalse();
        assertThat(task.getDueDate()).isNull();
        assertThat(task.getTodoListId()).isNull();
    }

    @Test
    void shouldHandleTaskWithMinimalData() {
        // Given
        String title = "Minimal Task";
        Task task = new Task(null, title, null, false, null, null);

        // Then
        assertThat(task).isNotNull();
        assertThat(task.getId()).isNull();
        assertThat(task.getTitle()).isEqualTo(title);
        assertThat(task.getDescription()).isNull();
        assertThat(task.isCompleted()).isFalse();
        assertThat(task.getDueDate()).isNull();
        assertThat(task.getTodoListId()).isNull();
    }
} 