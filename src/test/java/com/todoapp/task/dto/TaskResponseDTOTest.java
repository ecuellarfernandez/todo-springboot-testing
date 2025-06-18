package com.todoapp.task.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskResponseDTOTest {

    @Test
    void create_WithValidData_ShouldCreateDTO() {
        // Given
        UUID id = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea de prueba";
        String description = "Descripción de la tarea";
        boolean completed = false;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);

        // When
        TaskResponseDTO dto = new TaskResponseDTO(id, title, description, completed, dueDate, todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.id());
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(completed, dto.completed());
        assertEquals(dueDate, dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithNullId_ShouldCreateDTO() {
        // Given
        String title = "Tarea de prueba";
        String description = "Descripción de la tarea";
        boolean completed = false;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);
        UUID todoListId = UUID.randomUUID();

        // When
        TaskResponseDTO dto = new TaskResponseDTO(null, title, description, completed, dueDate, todoListId);

        // Then
        assertNotNull(dto);
        assertNull(dto.id());
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(completed, dto.completed());
        assertEquals(dueDate, dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithNullTitle_ShouldCreateDTO() {
        // Given
        UUID id = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String description = "Descripción de la tarea";
        boolean completed = true;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);

        // When
        TaskResponseDTO dto = new TaskResponseDTO(id, null, description, completed, dueDate, todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.id());
        assertNull(dto.title());
        assertEquals(description, dto.description());
        assertEquals(completed, dto.completed());
        assertEquals(dueDate, dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithNullDescription_ShouldCreateDTO() {
        // Given
        UUID id = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea de prueba";
        boolean completed = false;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);

        // When
        TaskResponseDTO dto = new TaskResponseDTO(id, title, null, completed, dueDate, todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.id());
        assertEquals(title, dto.title());
        assertNull(dto.description());
        assertEquals(completed, dto.completed());
        assertEquals(dueDate, dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithNullDueDate_ShouldCreateDTO() {
        // Given
        UUID id = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea de prueba";
        String description = "Descripción de la tarea";
        boolean completed = true;

        // When
        TaskResponseDTO dto = new TaskResponseDTO(id, title, description, completed, null, todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.id());
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(completed, dto.completed());
        assertNull(dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithNullTodoListId_ShouldCreateDTO() {
        // Given
        UUID id = UUID.randomUUID();
        String title = "Tarea de prueba";
        String description = "Descripción de la tarea";
        boolean completed = false;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);

        // When
        TaskResponseDTO dto = new TaskResponseDTO(id, title, description, completed, dueDate, null);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.id());
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(completed, dto.completed());
        assertEquals(dueDate, dto.dueDate());
        assertNull(dto.todoListId());
    }

    @Test
    void create_WithCompletedTrue_ShouldCreateDTO() {
        // Given
        UUID id = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea completada";
        String description = "Esta tarea está completada";
        boolean completed = true;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);

        // When
        TaskResponseDTO dto = new TaskResponseDTO(id, title, description, completed, dueDate, todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.id());
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertTrue(dto.completed());
        assertEquals(dueDate, dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithCompletedFalse_ShouldCreateDTO() {
        // Given
        UUID id = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea pendiente";
        String description = "Esta tarea está pendiente";
        boolean completed = false;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);

        // When
        TaskResponseDTO dto = new TaskResponseDTO(id, title, description, completed, dueDate, todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.id());
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertFalse(dto.completed());
        assertEquals(dueDate, dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithEmptyStrings_ShouldCreateDTO() {
        // Given
        UUID id = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "";
        String description = "";
        boolean completed = false;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);

        // When
        TaskResponseDTO dto = new TaskResponseDTO(id, title, description, completed, dueDate, todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.id());
        assertEquals(title, dto.title());
        assertTrue(dto.title().isEmpty());
        assertEquals(description, dto.description());
        assertTrue(dto.description().isEmpty());
        assertEquals(completed, dto.completed());
        assertEquals(dueDate, dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithSpecialCharacters_ShouldCreateDTO() {
        // Given
        UUID id = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea con caracteres especiales: áéíóú ñ @#$%^&*()";
        String description = "Descripción con símbolos: ©®™ €£¥ ¢∞§¶•ªº";
        boolean completed = true;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);

        // When
        TaskResponseDTO dto = new TaskResponseDTO(id, title, description, completed, dueDate, todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.id());
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(completed, dto.completed());
        assertEquals(dueDate, dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithUnicodeCharacters_ShouldCreateDTO() {
        // Given
        UUID id = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea con emojis 🚀📝✅";
        String description = "Descripción con unicode: 🌟✨💫";
        boolean completed = false;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);

        // When
        TaskResponseDTO dto = new TaskResponseDTO(id, title, description, completed, dueDate, todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.id());
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(completed, dto.completed());
        assertEquals(dueDate, dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithLongStrings_ShouldCreateDTO() {
        // Given
        UUID id = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "A".repeat(1000);
        String description = "B".repeat(2000);
        boolean completed = true;
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);

        // When
        TaskResponseDTO dto = new TaskResponseDTO(id, title, description, completed, dueDate, todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.id());
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(completed, dto.completed());
        assertEquals(dueDate, dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithPastDueDate_ShouldCreateDTO() {
        // Given
        UUID id = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea vencida";
        String description = "Esta tarea ya venció";
        boolean completed = false;
        LocalDateTime pastDueDate = LocalDateTime.now().minusDays(5);

        // When
        TaskResponseDTO dto = new TaskResponseDTO(id, title, description, completed, pastDueDate, todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.id());
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(completed, dto.completed());
        assertEquals(pastDueDate, dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithFutureDueDate_ShouldCreateDTO() {
        // Given
        UUID id = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea futura";
        String description = "Esta tarea es para el futuro";
        boolean completed = false;
        LocalDateTime futureDueDate = LocalDateTime.now().plusDays(30);

        // When
        TaskResponseDTO dto = new TaskResponseDTO(id, title, description, completed, futureDueDate, todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.id());
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(completed, dto.completed());
        assertEquals(futureDueDate, dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithTodayDueDate_ShouldCreateDTO() {
        // Given
        UUID id = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea para hoy";
        String description = "Esta tarea es para hoy";
        boolean completed = false;
        LocalDateTime todayDueDate = LocalDateTime.now();

        // When
        TaskResponseDTO dto = new TaskResponseDTO(id, title, description, completed, todayDueDate, todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.id());
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(completed, dto.completed());
        assertEquals(todayDueDate, dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithMinimalData_ShouldCreateDTO() {
        // Given
        UUID id = UUID.randomUUID();
        String title = "Tarea mínima";

        // When
        TaskResponseDTO dto = new TaskResponseDTO(id, title, null, false, null, null);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.id());
        assertEquals(title, dto.title());
        assertNull(dto.description());
        assertFalse(dto.completed());
        assertNull(dto.dueDate());
        assertNull(dto.todoListId());
    }

    @Test
    void create_WithAllNullValues_ShouldCreateDTO() {
        // Given
        // When
        TaskResponseDTO dto = new TaskResponseDTO(null, null, null, false, null, null);

        // Then
        assertNotNull(dto);
        assertNull(dto.id());
        assertNull(dto.title());
        assertNull(dto.description());
        assertFalse(dto.completed());
        assertNull(dto.dueDate());
        assertNull(dto.todoListId());
    }

    @Test
    void create_WithAllEmptyStrings_ShouldCreateDTO() {
        // Given
        UUID id = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();

        // When
        TaskResponseDTO dto = new TaskResponseDTO(id, "", "", false, LocalDateTime.now().plusDays(1), todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.id());
        assertEquals("", dto.title());
        assertEquals("", dto.description());
        assertFalse(dto.completed());
        assertNotNull(dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithWhitespaceStrings_ShouldCreateDTO() {
        // Given
        UUID id = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "   ";
        String description = "\t\n";

        // When
        TaskResponseDTO dto = new TaskResponseDTO(id, title, description, false, LocalDateTime.now().plusDays(1), todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.id());
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertFalse(dto.completed());
        assertNotNull(dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithNumbersInTitle_ShouldCreateDTO() {
        // Given
        UUID id = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea 123 con números 456";
        String description = "Descripción con números 789";

        // When
        TaskResponseDTO dto = new TaskResponseDTO(id, title, description, true, LocalDateTime.now().plusDays(1), todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.id());
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertTrue(dto.completed());
        assertNotNull(dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithSingleCharacter_ShouldCreateDTO() {
        // Given
        UUID id = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "A";
        String description = "B";

        // When
        TaskResponseDTO dto = new TaskResponseDTO(id, title, description, false, LocalDateTime.now().plusDays(1), todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.id());
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertFalse(dto.completed());
        assertNotNull(dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithVeryLongTitle_ShouldCreateDTO() {
        // Given
        UUID id = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "T".repeat(10000);
        String description = "Descripción normal";

        // When
        TaskResponseDTO dto = new TaskResponseDTO(id, title, description, true, LocalDateTime.now().plusDays(1), todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.id());
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertTrue(dto.completed());
        assertNotNull(dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithVeryLongDescription_ShouldCreateDTO() {
        // Given
        UUID id = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "Título normal";
        String description = "D".repeat(50000);

        // When
        TaskResponseDTO dto = new TaskResponseDTO(id, title, description, false, LocalDateTime.now().plusDays(1), todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.id());
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertFalse(dto.completed());
        assertNotNull(dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithMultipleSpaces_ShouldCreateDTO() {
        // Given
        UUID id = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea    con    múltiples    espacios";
        String description = "Descripción   con   espacios   extra";

        // When
        TaskResponseDTO dto = new TaskResponseDTO(id, title, description, false, LocalDateTime.now().plusDays(1), todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.id());
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertFalse(dto.completed());
        assertNotNull(dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithNewlinesInDescription_ShouldCreateDTO() {
        // Given
        UUID id = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea con saltos de línea";
        String description = "Línea 1\nLínea 2\nLínea 3";

        // When
        TaskResponseDTO dto = new TaskResponseDTO(id, title, description, true, LocalDateTime.now().plusDays(1), todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.id());
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertTrue(dto.completed());
        assertNotNull(dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithTabsInDescription_ShouldCreateDTO() {
        // Given
        UUID id = UUID.randomUUID();
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea con tabulaciones";
        String description = "Columna1\tColumna2\tColumna3";

        // When
        TaskResponseDTO dto = new TaskResponseDTO(id, title, description, false, LocalDateTime.now().plusDays(1), todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(id, dto.id());
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertFalse(dto.completed());
        assertNotNull(dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithDifferentUUIDs_ShouldCreateDTO() {
        // Given
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        UUID todoListId1 = UUID.randomUUID();
        UUID todoListId2 = UUID.randomUUID();
        String title = "Tarea con diferentes UUIDs";

        // When
        TaskResponseDTO dto1 = new TaskResponseDTO(id1, title, "Descripción 1", false, LocalDateTime.now().plusDays(1), todoListId1);
        TaskResponseDTO dto2 = new TaskResponseDTO(id2, title, "Descripción 2", true, LocalDateTime.now().plusDays(2), todoListId2);

        // Then
        assertNotNull(dto1);
        assertNotNull(dto2);
        assertEquals(id1, dto1.id());
        assertEquals(id2, dto2.id());
        assertEquals(todoListId1, dto1.todoListId());
        assertEquals(todoListId2, dto2.todoListId());
        assertNotEquals(dto1.id(), dto2.id());
        assertNotEquals(dto1.todoListId(), dto2.todoListId());
    }
} 