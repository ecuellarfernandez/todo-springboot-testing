package com.todoapp.task.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class TaskStatusUpdateDTOTest {

    @Test
    void shouldStoreCompletedTrue() {
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(true);
        
        assertThat(dto.completed()).isTrue();
    }

    @Test
    void shouldStoreCompletedFalse() {
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(false);
        
        assertThat(dto.completed()).isFalse();
    }

    @Test
    void shouldBeImmutable() {
        TaskStatusUpdateDTO dto1 = new TaskStatusUpdateDTO(true);
        TaskStatusUpdateDTO dto2 = new TaskStatusUpdateDTO(true);
        
        assertThat(dto1).isEqualTo(dto2);
    }

    @Test
    void shouldHandleDifferentValues() {
        TaskStatusUpdateDTO completedTrue = new TaskStatusUpdateDTO(true);
        TaskStatusUpdateDTO completedFalse = new TaskStatusUpdateDTO(false);
        
        assertThat(completedTrue.completed()).isTrue();
        assertThat(completedFalse.completed()).isFalse();
        assertThat(completedTrue).isNotEqualTo(completedFalse);
    }

    @Test
    void shouldHandleMultipleInstances() {
        TaskStatusUpdateDTO dto1 = new TaskStatusUpdateDTO(true);
        TaskStatusUpdateDTO dto2 = new TaskStatusUpdateDTO(true);
        TaskStatusUpdateDTO dto3 = new TaskStatusUpdateDTO(false);
        
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1).isNotEqualTo(dto3);
        assertThat(dto2).isNotEqualTo(dto3);
    }

    @Test
    void shouldHaveConsistentHashCode() {
        TaskStatusUpdateDTO dto1 = new TaskStatusUpdateDTO(true);
        TaskStatusUpdateDTO dto2 = new TaskStatusUpdateDTO(true);
        
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    void shouldHaveDifferentHashCodeForDifferentValues() {
        TaskStatusUpdateDTO dto1 = new TaskStatusUpdateDTO(true);
        TaskStatusUpdateDTO dto2 = new TaskStatusUpdateDTO(false);
        
        assertThat(dto1.hashCode()).isNotEqualTo(dto2.hashCode());
    }

    @Test
    void shouldHaveMeaningfulToString() {
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(true);
        String toString = dto.toString();
        
        assertThat(toString).contains("TaskStatusUpdateDTO");
        assertThat(toString).contains("completed=true");
    }

    @Test
    void shouldHandleToStringForFalseValue() {
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(false);
        String toString = dto.toString();
        
        assertThat(toString).contains("TaskStatusUpdateDTO");
        assertThat(toString).contains("completed=false");
    }
} 