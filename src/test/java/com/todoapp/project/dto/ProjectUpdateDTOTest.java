package com.todoapp.project.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ProjectUpdateDTOTest {

    @Test
    void shouldStoreValues() {
        ProjectUpdateDTO dto = new ProjectUpdateDTO("Updated Project", "Updated Description");
        
        assertThat(dto.name()).isEqualTo("Updated Project");
        assertThat(dto.description()).isEqualTo("Updated Description");
    }

    @Test
    void shouldBeImmutable() {
        ProjectUpdateDTO dto1 = new ProjectUpdateDTO("Project A", "Description A");
        ProjectUpdateDTO dto2 = new ProjectUpdateDTO("Project A", "Description A");
        
        assertThat(dto1).isEqualTo(dto2);
    }

    @Test
    void shouldHandleNullValues() {
        ProjectUpdateDTO dto = new ProjectUpdateDTO(null, null);
        
        assertThat(dto.name()).isNull();
        assertThat(dto.description()).isNull();
    }

    @Test
    void shouldHandlePartialUpdates() {
        ProjectUpdateDTO nameOnly = new ProjectUpdateDTO("Updated Name", null);
        ProjectUpdateDTO descriptionOnly = new ProjectUpdateDTO(null, "Updated Description");
        
        assertThat(nameOnly.name()).isEqualTo("Updated Name");
        assertThat(nameOnly.description()).isNull();
        
        assertThat(descriptionOnly.name()).isNull();
        assertThat(descriptionOnly.description()).isEqualTo("Updated Description");
    }

    @Test
    void shouldHaveConsistentHashCode() {
        ProjectUpdateDTO dto1 = new ProjectUpdateDTO("Project A", "Description A");
        ProjectUpdateDTO dto2 = new ProjectUpdateDTO("Project A", "Description A");
        
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    void shouldHaveMeaningfulToString() {
        ProjectUpdateDTO dto = new ProjectUpdateDTO("Test Project", "Test Description");
        String toString = dto.toString();
        
        assertThat(toString).contains("Test Project");
        assertThat(toString).contains("Test Description");
    }

    @Test
    void shouldHandleEmptyStrings() {
        ProjectUpdateDTO dto = new ProjectUpdateDTO("", "");
        
        assertThat(dto.name()).isEmpty();
        assertThat(dto.description()).isEmpty();
    }
} 