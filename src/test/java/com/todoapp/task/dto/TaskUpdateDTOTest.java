package com.todoapp.task.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskUpdateDTOTest {

    @Test
    void create_WithValidData_ShouldCreateDTO() {
        // Given
        String title = "Tarea actualizada";
        String description = "Descripción actualizada";
        LocalDateTime dueDate = LocalDateTime.now().plusDays(2);

        // When
        TaskUpdateDTO dto = new TaskUpdateDTO(title, description, dueDate);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(dueDate, dto.dueDate());
    }

    @Test
    void create_WithNullTitle_ShouldCreateDTO() {
        // Given
        String description = "Descripción actualizada";
        LocalDateTime dueDate = LocalDateTime.now().plusDays(2);

        // When
        TaskUpdateDTO dto = new TaskUpdateDTO(null, description, dueDate);

        // Then
        assertNotNull(dto);
        assertNull(dto.title());
        assertEquals(description, dto.description());
        assertEquals(dueDate, dto.dueDate());
    }

    @Test
    void create_WithNullDescription_ShouldCreateDTO() {
        // Given
        String title = "Tarea actualizada";
        LocalDateTime dueDate = LocalDateTime.now().plusDays(2);

        // When
        TaskUpdateDTO dto = new TaskUpdateDTO(title, null, dueDate);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertNull(dto.description());
        assertEquals(dueDate, dto.dueDate());
    }

    @Test
    void create_WithNullDueDate_ShouldCreateDTO() {
        // Given
        String title = "Tarea actualizada";
        String description = "Descripción actualizada";

        // When
        TaskUpdateDTO dto = new TaskUpdateDTO(title, description, null);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertNull(dto.dueDate());
    }

    @Test
    void create_WithEmptyStrings_ShouldCreateDTO() {
        // Given
        String title = "";
        String description = "";
        LocalDateTime dueDate = LocalDateTime.now().plusDays(2);

        // When
        TaskUpdateDTO dto = new TaskUpdateDTO(title, description, dueDate);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertTrue(dto.title().isEmpty());
        assertEquals(description, dto.description());
        assertTrue(dto.description().isEmpty());
        assertEquals(dueDate, dto.dueDate());
    }

    @Test
    void create_WithSpecialCharacters_ShouldCreateDTO() {
        // Given
        String title = "Tarea con caracteres especiales: áéíóú ñ @#$%^&*()";
        String description = "Descripción con símbolos: ©®™ €£¥ ¢∞§¶•ªº";
        LocalDateTime dueDate = LocalDateTime.now().plusDays(2);

        // When
        TaskUpdateDTO dto = new TaskUpdateDTO(title, description, dueDate);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(dueDate, dto.dueDate());
    }

    @Test
    void create_WithUnicodeCharacters_ShouldCreateDTO() {
        // Given
        String title = "Tarea con emojis 🚀📝✅";
        String description = "Descripción con unicode: 🌟✨💫";
        LocalDateTime dueDate = LocalDateTime.now().plusDays(2);

        // When
        TaskUpdateDTO dto = new TaskUpdateDTO(title, description, dueDate);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(dueDate, dto.dueDate());
    }

    @Test
    void create_WithLongStrings_ShouldCreateDTO() {
        // Given
        String title = "A".repeat(1000);
        String description = "B".repeat(2000);
        LocalDateTime dueDate = LocalDateTime.now().plusDays(2);

        // When
        TaskUpdateDTO dto = new TaskUpdateDTO(title, description, dueDate);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(dueDate, dto.dueDate());
    }

    @Test
    void create_WithPastDueDate_ShouldCreateDTO() {
        // Given
        String title = "Tarea vencida";
        String description = "Esta tarea ya venció";
        LocalDateTime pastDueDate = LocalDateTime.now().minusDays(5);

        // When
        TaskUpdateDTO dto = new TaskUpdateDTO(title, description, pastDueDate);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(pastDueDate, dto.dueDate());
    }

    @Test
    void create_WithFutureDueDate_ShouldCreateDTO() {
        // Given
        String title = "Tarea futura";
        String description = "Esta tarea es para el futuro";
        LocalDateTime futureDueDate = LocalDateTime.now().plusDays(30);

        // When
        TaskUpdateDTO dto = new TaskUpdateDTO(title, description, futureDueDate);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(futureDueDate, dto.dueDate());
    }

    @Test
    void create_WithTodayDueDate_ShouldCreateDTO() {
        // Given
        String title = "Tarea para hoy";
        String description = "Esta tarea es para hoy";
        LocalDateTime todayDueDate = LocalDateTime.now();

        // When
        TaskUpdateDTO dto = new TaskUpdateDTO(title, description, todayDueDate);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(todayDueDate, dto.dueDate());
    }

    @Test
    void create_WithOnlyTitle_ShouldCreateDTO() {
        // Given
        String title = "Solo título";

        // When
        TaskUpdateDTO dto = new TaskUpdateDTO(title, null, null);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertNull(dto.description());
        assertNull(dto.dueDate());
    }

    @Test
    void create_WithOnlyDescription_ShouldCreateDTO() {
        // Given
        String description = "Solo descripción";

        // When
        TaskUpdateDTO dto = new TaskUpdateDTO(null, description, null);

        // Then
        assertNotNull(dto);
        assertNull(dto.title());
        assertEquals(description, dto.description());
        assertNull(dto.dueDate());
    }

    @Test
    void create_WithOnlyDueDate_ShouldCreateDTO() {
        // Given
        LocalDateTime dueDate = LocalDateTime.now().plusDays(5);

        // When
        TaskUpdateDTO dto = new TaskUpdateDTO(null, null, dueDate);

        // Then
        assertNotNull(dto);
        assertNull(dto.title());
        assertNull(dto.description());
        assertEquals(dueDate, dto.dueDate());
    }

    @Test
    void create_WithAllNullValues_ShouldCreateDTO() {
        // Given
        // When
        TaskUpdateDTO dto = new TaskUpdateDTO(null, null, null);

        // Then
        assertNotNull(dto);
        assertNull(dto.title());
        assertNull(dto.description());
        assertNull(dto.dueDate());
    }

    @Test
    void create_WithWhitespaceStrings_ShouldCreateDTO() {
        // Given
        String title = "   ";
        String description = "\t\n";
        LocalDateTime dueDate = LocalDateTime.now().plusDays(2);

        // When
        TaskUpdateDTO dto = new TaskUpdateDTO(title, description, dueDate);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(dueDate, dto.dueDate());
    }

    @Test
    void create_WithNumbersInTitle_ShouldCreateDTO() {
        // Given
        String title = "Tarea 123 con números 456";
        String description = "Descripción con números 789";
        LocalDateTime dueDate = LocalDateTime.now().plusDays(2);

        // When
        TaskUpdateDTO dto = new TaskUpdateDTO(title, description, dueDate);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(dueDate, dto.dueDate());
    }

    @Test
    void create_WithSingleCharacter_ShouldCreateDTO() {
        // Given
        String title = "A";
        String description = "B";
        LocalDateTime dueDate = LocalDateTime.now().plusDays(2);

        // When
        TaskUpdateDTO dto = new TaskUpdateDTO(title, description, dueDate);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(dueDate, dto.dueDate());
    }

    @Test
    void create_WithVeryLongTitle_ShouldCreateDTO() {
        // Given
        String title = "T".repeat(10000);
        String description = "Descripción normal";
        LocalDateTime dueDate = LocalDateTime.now().plusDays(2);

        // When
        TaskUpdateDTO dto = new TaskUpdateDTO(title, description, dueDate);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(dueDate, dto.dueDate());
    }

    @Test
    void create_WithVeryLongDescription_ShouldCreateDTO() {
        // Given
        String title = "Título normal";
        String description = "D".repeat(50000);
        LocalDateTime dueDate = LocalDateTime.now().plusDays(2);

        // When
        TaskUpdateDTO dto = new TaskUpdateDTO(title, description, dueDate);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(dueDate, dto.dueDate());
    }

    @Test
    void create_WithMultipleSpaces_ShouldCreateDTO() {
        // Given
        String title = "Tarea    con    múltiples    espacios";
        String description = "Descripción   con   espacios   extra";
        LocalDateTime dueDate = LocalDateTime.now().plusDays(2);

        // When
        TaskUpdateDTO dto = new TaskUpdateDTO(title, description, dueDate);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(dueDate, dto.dueDate());
    }

    @Test
    void create_WithNewlinesInDescription_ShouldCreateDTO() {
        // Given
        String title = "Tarea con saltos de línea";
        String description = "Línea 1\nLínea 2\nLínea 3";
        LocalDateTime dueDate = LocalDateTime.now().plusDays(2);

        // When
        TaskUpdateDTO dto = new TaskUpdateDTO(title, description, dueDate);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(dueDate, dto.dueDate());
    }

    @Test
    void create_WithTabsInDescription_ShouldCreateDTO() {
        // Given
        String title = "Tarea con tabulaciones";
        String description = "Columna1\tColumna2\tColumna3";
        LocalDateTime dueDate = LocalDateTime.now().plusDays(2);

        // When
        TaskUpdateDTO dto = new TaskUpdateDTO(title, description, dueDate);

        // Then
        assertNotNull(dto);
        assertEquals(title, dto.title());
        assertEquals(description, dto.description());
        assertEquals(dueDate, dto.dueDate());
    }

    @Test
    void create_WithDifferentDueDates_ShouldCreateDTO() {
        // Given
        String title = "Tarea con diferentes fechas";
        String description = "Descripción";
        LocalDateTime dueDate1 = LocalDateTime.now().plusDays(1);
        LocalDateTime dueDate2 = LocalDateTime.now().plusDays(7);

        // When
        TaskUpdateDTO dto1 = new TaskUpdateDTO(title, description, dueDate1);
        TaskUpdateDTO dto2 = new TaskUpdateDTO(title, description, dueDate2);

        // Then
        assertNotNull(dto1);
        assertNotNull(dto2);
        assertEquals(dueDate1, dto1.dueDate());
        assertEquals(dueDate2, dto2.dueDate());
        assertNotEquals(dto1.dueDate(), dto2.dueDate());
    }

    @Test
    void create_WithSameTitleAndDescription_ShouldCreateDTO() {
        // Given
        String title = "Tarea idéntica";
        String description = "Descripción idéntica";
        LocalDateTime dueDate1 = LocalDateTime.now().plusDays(1);
        LocalDateTime dueDate2 = LocalDateTime.now().plusDays(2);

        // When
        TaskUpdateDTO dto1 = new TaskUpdateDTO(title, description, dueDate1);
        TaskUpdateDTO dto2 = new TaskUpdateDTO(title, description, dueDate2);

        // Then
        assertNotNull(dto1);
        assertNotNull(dto2);
        assertEquals(title, dto1.title());
        assertEquals(title, dto2.title());
        assertEquals(description, dto1.description());
        assertEquals(description, dto2.description());
        assertEquals(dueDate1, dto1.dueDate());
        assertEquals(dueDate2, dto2.dueDate());
    }
} 