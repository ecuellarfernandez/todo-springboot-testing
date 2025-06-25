package com.todoapp.task.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;

class TaskUpdateDTOTest {

    @Test
    void shouldStoreValues() {
        LocalDate dueDate = LocalDate.now().plusDays(7);
        TaskUpdateDTO dto = new TaskUpdateDTO("Updated Task", "Updated Description", dueDate);
        
        assertThat(dto.title()).isEqualTo("Updated Task");
        assertThat(dto.description()).isEqualTo("Updated Description");
        assertThat(dto.dueDate()).isEqualTo(dueDate);
    }

    @Test
    void shouldBeImmutable() {
        LocalDate dueDate = LocalDate.now().plusDays(7);
        TaskUpdateDTO dto1 = new TaskUpdateDTO("Updated Task", "Updated Description", dueDate);
        TaskUpdateDTO dto2 = new TaskUpdateDTO("Updated Task", "Updated Description", dueDate);
        
        assertThat(dto1).isEqualTo(dto2);
    }

    @Test
    void shouldHandleNullValues() {
        TaskUpdateDTO dto = new TaskUpdateDTO(null, null, null);
        
        assertThat(dto.title()).isNull();
        assertThat(dto.description()).isNull();
        assertThat(dto.dueDate()).isNull();
    }

    @Test
    void shouldHandleEmptyStrings() {
        LocalDate dueDate = LocalDate.now().plusDays(7);
        TaskUpdateDTO dto = new TaskUpdateDTO("", "", dueDate);
        
        assertThat(dto.title()).isEqualTo("");
        assertThat(dto.description()).isEqualTo("");
        assertThat(dto.dueDate()).isEqualTo(dueDate);
    }

    @Test
    void shouldHandlePartialUpdates() {
        // Only title update
        TaskUpdateDTO titleOnly = new TaskUpdateDTO("New Title", null, null);
        assertThat(titleOnly.title()).isEqualTo("New Title");
        assertThat(titleOnly.description()).isNull();
        assertThat(titleOnly.dueDate()).isNull();

        // Only description update
        TaskUpdateDTO descriptionOnly = new TaskUpdateDTO(null, "New Description", null);
        assertThat(descriptionOnly.title()).isNull();
        assertThat(descriptionOnly.description()).isEqualTo("New Description");
        assertThat(descriptionOnly.dueDate()).isNull();

        // Only due date update
        LocalDate newDueDate = LocalDate.now().plusDays(14);
        TaskUpdateDTO dueDateOnly = new TaskUpdateDTO(null, null, newDueDate);
        assertThat(dueDateOnly.title()).isNull();
        assertThat(dueDateOnly.description()).isNull();
        assertThat(dueDateOnly.dueDate()).isEqualTo(newDueDate);
    }

    @Test
    void shouldHandlePastDueDate() {
        LocalDate pastDate = LocalDate.now().minusDays(1);
        TaskUpdateDTO dto = new TaskUpdateDTO("Task", "Description", pastDate);
        
        assertThat(dto.dueDate()).isEqualTo(pastDate);
    }

    @Test
    void shouldHandleFutureDueDate() {
        LocalDate futureDate = LocalDate.now().plusDays(30);
        TaskUpdateDTO dto = new TaskUpdateDTO("Task", "Description", futureDate);
        
        assertThat(dto.dueDate()).isEqualTo(futureDate);
    }

    @Test
    void shouldHandleTodayDueDate() {
        LocalDate today = LocalDate.now();
        TaskUpdateDTO dto = new TaskUpdateDTO("Task", "Description", today);
        
        assertThat(dto.dueDate()).isEqualTo(today);
    }

    @Test
    void shouldHandleLongStrings() {
        String longTitle = "A".repeat(1000);
        String longDescription = "B".repeat(2000);
        LocalDate dueDate = LocalDate.now().plusDays(7);
        
        TaskUpdateDTO dto = new TaskUpdateDTO(longTitle, longDescription, dueDate);
        
        assertThat(dto.title()).isEqualTo(longTitle);
        assertThat(dto.description()).isEqualTo(longDescription);
        assertThat(dto.dueDate()).isEqualTo(dueDate);
    }

    @Test
    void shouldHandleSpecialCharacters() {
        String titleWithSpecialChars = "Task with special chars: áéíóú ñ @#$%^&*()";
        String descriptionWithSpecialChars = "Description with emojis: 🚀 📝 ✅";
        LocalDate dueDate = LocalDate.now().plusDays(7);
        
        TaskUpdateDTO dto = new TaskUpdateDTO(titleWithSpecialChars, descriptionWithSpecialChars, dueDate);
        
        assertThat(dto.title()).isEqualTo(titleWithSpecialChars);
        assertThat(dto.description()).isEqualTo(descriptionWithSpecialChars);
        assertThat(dto.dueDate()).isEqualTo(dueDate);
    }
} 