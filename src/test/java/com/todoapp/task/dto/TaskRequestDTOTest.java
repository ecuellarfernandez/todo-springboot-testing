package com.todoapp.task.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskRequestDTOTest {

    @Test
    void create_WithValidData_ShouldCreateDTO() {
        // Given
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea de prueba";
        String description = "Descripción de la tarea";
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);

        // When
        TaskRequestDTO dto = new TaskRequestDTO(title, description, dueDate, todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(dueDate, dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithNullDescription_ShouldCreateDTO() {
        // Given
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea de prueba";
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);

        // When
        TaskRequestDTO dto = new TaskRequestDTO(title, null, dueDate, todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertNull(dto.description());
        assertEquals(dueDate, dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithNullDueDate_ShouldCreateDTO() {
        // Given
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea de prueba";
        String description = "Descripción de la tarea";

        // When
        TaskRequestDTO dto = new TaskRequestDTO(title, description, null, todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertNull(dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithEmptyDescription_ShouldCreateDTO() {
        // Given
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea de prueba";
        String description = "";
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);

        // When
        TaskRequestDTO dto = new TaskRequestDTO(title, description, dueDate, todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertTrue(dto.description().isEmpty());
        assertEquals(dueDate, dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithEmptyTitle_ShouldCreateDTO() {
        // Given
        UUID todoListId = UUID.randomUUID();
        String title = "";
        String description = "Descripción de la tarea";
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);

        // When
        TaskRequestDTO dto = new TaskRequestDTO(title, description, dueDate, todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertTrue(dto.title().isEmpty());
        assertEquals(description, dto.description());
        assertEquals(dueDate, dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithSpecialCharacters_ShouldCreateDTO() {
        // Given
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea con caracteres especiales: áéíóú ñ @#$%^&*()";
        String description = "Descripción con símbolos: ©®™ €£¥ ¢∞§¶•ªº";
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);

        // When
        TaskRequestDTO dto = new TaskRequestDTO(title, description, dueDate, todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(dueDate, dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithUnicodeCharacters_ShouldCreateDTO() {
        // Given
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea con emojis 🚀📝✅";
        String description = "Descripción con unicode: 🌟✨💫";
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);

        // When
        TaskRequestDTO dto = new TaskRequestDTO(title, description, dueDate, todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(dueDate, dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithLongStrings_ShouldCreateDTO() {
        // Given
        UUID todoListId = UUID.randomUUID();
        String title = "A".repeat(1000);
        String description = "B".repeat(2000);
        LocalDateTime dueDate = LocalDateTime.now().plusDays(1);

        // When
        TaskRequestDTO dto = new TaskRequestDTO(title, description, dueDate, todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(dueDate, dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithPastDueDate_ShouldCreateDTO() {
        // Given
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea vencida";
        String description = "Esta tarea ya venció";
        LocalDateTime pastDueDate = LocalDateTime.now().minusDays(5);

        // When
        TaskRequestDTO dto = new TaskRequestDTO(title, description, pastDueDate, todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(pastDueDate, dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithFutureDueDate_ShouldCreateDTO() {
        // Given
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea futura";
        String description = "Esta tarea es para el futuro";
        LocalDateTime futureDueDate = LocalDateTime.now().plusDays(30);

        // When
        TaskRequestDTO dto = new TaskRequestDTO(title, description, futureDueDate, todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(futureDueDate, dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithTodayDueDate_ShouldCreateDTO() {
        // Given
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea para hoy";
        String description = "Esta tarea es para hoy";
        LocalDateTime todayDueDate = LocalDateTime.now();

        // When
        TaskRequestDTO dto = new TaskRequestDTO(title, description, todayDueDate, todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(todayDueDate, dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithMinimalData_ShouldCreateDTO() {
        // Given
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea mínima";

        // When
        TaskRequestDTO dto = new TaskRequestDTO(title, null, null, todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertNull(dto.description());
        assertNull(dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithAllNullValues_ShouldCreateDTO() {
        // Given
        UUID todoListId = UUID.randomUUID();

        // When
        TaskRequestDTO dto = new TaskRequestDTO(null, null, null, todoListId);

        // Then
        assertNotNull(dto);
        assertNull(dto.title());
        assertNull(dto.description());
        assertNull(dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithAllEmptyStrings_ShouldCreateDTO() {
        // Given
        UUID todoListId = UUID.randomUUID();

        // When
        TaskRequestDTO dto = new TaskRequestDTO("", "", LocalDateTime.now().plusDays(1), todoListId);

        // Then
        assertNotNull(dto);
        assertEquals("", dto.title());
        assertEquals("", dto.description());
        assertNotNull(dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithWhitespaceStrings_ShouldCreateDTO() {
        // Given
        UUID todoListId = UUID.randomUUID();
        String title = "   ";
        String description = "\t\n";

        // When
        TaskRequestDTO dto = new TaskRequestDTO(title, description, LocalDateTime.now().plusDays(1), todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertNotNull(dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithNumbersInTitle_ShouldCreateDTO() {
        // Given
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea 123 con números 456";
        String description = "Descripción con números 789";

        // When
        TaskRequestDTO dto = new TaskRequestDTO(title, description, LocalDateTime.now().plusDays(1), todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertNotNull(dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithSingleCharacter_ShouldCreateDTO() {
        // Given
        UUID todoListId = UUID.randomUUID();
        String title = "A";
        String description = "B";

        // When
        TaskRequestDTO dto = new TaskRequestDTO(title, description, LocalDateTime.now().plusDays(1), todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertNotNull(dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithVeryLongTitle_ShouldCreateDTO() {
        // Given
        UUID todoListId = UUID.randomUUID();
        String title = "T".repeat(10000);
        String description = "Descripción normal";

        // When
        TaskRequestDTO dto = new TaskRequestDTO(title, description, LocalDateTime.now().plusDays(1), todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertNotNull(dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithVeryLongDescription_ShouldCreateDTO() {
        // Given
        UUID todoListId = UUID.randomUUID();
        String title = "Título normal";
        String description = "D".repeat(50000);

        // When
        TaskRequestDTO dto = new TaskRequestDTO(title, description, LocalDateTime.now().plusDays(1), todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertNotNull(dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithMultipleSpaces_ShouldCreateDTO() {
        // Given
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea    con    múltiples    espacios";
        String description = "Descripción   con   espacios   extra";

        // When
        TaskRequestDTO dto = new TaskRequestDTO(title, description, LocalDateTime.now().plusDays(1), todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertNotNull(dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithNewlinesInDescription_ShouldCreateDTO() {
        // Given
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea con saltos de línea";
        String description = "Línea 1\nLínea 2\nLínea 3";

        // When
        TaskRequestDTO dto = new TaskRequestDTO(title, description, LocalDateTime.now().plusDays(1), todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertNotNull(dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithTabsInDescription_ShouldCreateDTO() {
        // Given
        UUID todoListId = UUID.randomUUID();
        String title = "Tarea con tabulaciones";
        String description = "Columna1\tColumna2\tColumna3";

        // When
        TaskRequestDTO dto = new TaskRequestDTO(title, description, LocalDateTime.now().plusDays(1), todoListId);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertNotNull(dto.dueDate());
        assertEquals(todoListId, dto.todoListId());
    }

    @Test
    void create_WithDifferentUUIDs_ShouldCreateDTO() {
        // Given
        UUID todoListId1 = UUID.randomUUID();
        UUID todoListId2 = UUID.randomUUID();
        String title = "Tarea con diferentes UUIDs";

        // When
        TaskRequestDTO dto1 = new TaskRequestDTO(title, "Descripción 1", LocalDateTime.now().plusDays(1), todoListId1);
        TaskRequestDTO dto2 = new TaskRequestDTO(title, "Descripción 2", LocalDateTime.now().plusDays(2), todoListId2);

        // Then
        assertNotNull(dto1);
        assertNotNull(dto2);
        assertEquals(todoListId1, dto1.todoListId());
        assertEquals(todoListId2, dto2.todoListId());
        assertNotEquals(dto1.todoListId(), dto2.todoListId());
    }
} 