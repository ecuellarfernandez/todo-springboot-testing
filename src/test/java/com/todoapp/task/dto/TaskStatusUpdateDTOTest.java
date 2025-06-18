package com.todoapp.task.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskStatusUpdateDTOTest {

    @Test
    void create_WithCompletedTrue_ShouldCreateDTO() {
        // Given
        boolean completed = true;

        // When
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(completed);

        // Then
        assertNotNull(dto);
        assertTrue(dto.completed());
    }

    @Test
    void create_WithCompletedFalse_ShouldCreateDTO() {
        // Given
        boolean completed = false;

        // When
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(completed);

        // Then
        assertNotNull(dto);
        assertFalse(dto.completed());
    }

    @Test
    void create_WithDefaultBooleanValue_ShouldCreateDTO() {
        // Given
        // When
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(false);

        // Then
        assertNotNull(dto);
        assertFalse(dto.completed());
    }

    @Test
    void create_MultipleInstancesWithSameValue_ShouldCreateDTOs() {
        // Given
        boolean completed = true;

        // When
        TaskStatusUpdateDTO dto1 = new TaskStatusUpdateDTO(completed);
        TaskStatusUpdateDTO dto2 = new TaskStatusUpdateDTO(completed);

        // Then
        assertNotNull(dto1);
        assertNotNull(dto2);
        assertTrue(dto1.completed());
        assertTrue(dto2.completed());
        assertEquals(dto1.completed(), dto2.completed());
    }

    @Test
    void create_MultipleInstancesWithDifferentValues_ShouldCreateDTOs() {
        // Given
        boolean completed1 = true;
        boolean completed2 = false;

        // When
        TaskStatusUpdateDTO dto1 = new TaskStatusUpdateDTO(completed1);
        TaskStatusUpdateDTO dto2 = new TaskStatusUpdateDTO(completed2);

        // Then
        assertNotNull(dto1);
        assertNotNull(dto2);
        assertTrue(dto1.completed());
        assertFalse(dto2.completed());
        assertNotEquals(dto1.completed(), dto2.completed());
    }

    @Test
    void create_WithExplicitTrue_ShouldCreateDTO() {
        // Given
        // When
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(Boolean.TRUE);

        // Then
        assertNotNull(dto);
        assertTrue(dto.completed());
    }

    @Test
    void create_WithExplicitFalse_ShouldCreateDTO() {
        // Given
        // When
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(Boolean.FALSE);

        // Then
        assertNotNull(dto);
        assertFalse(dto.completed());
    }

    @Test
    void create_WithBooleanExpression_ShouldCreateDTO() {
        // Given
        int value = 10;
        boolean completed = value > 5;

        // When
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(completed);

        // Then
        assertNotNull(dto);
        assertTrue(dto.completed());
    }

    @Test
    void create_WithBooleanExpressionFalse_ShouldCreateDTO() {
        // Given
        int value = 3;
        boolean completed = value > 5;

        // When
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(completed);

        // Then
        assertNotNull(dto);
        assertFalse(dto.completed());
    }

    @Test
    void create_WithStringComparison_ShouldCreateDTO() {
        // Given
        String status = "COMPLETED";
        boolean completed = "COMPLETED".equals(status);

        // When
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(completed);

        // Then
        assertNotNull(dto);
        assertTrue(dto.completed());
    }

    @Test
    void create_WithStringComparisonFalse_ShouldCreateDTO() {
        // Given
        String status = "PENDING";
        boolean completed = "COMPLETED".equals(status);

        // When
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(completed);

        // Then
        assertNotNull(dto);
        assertFalse(dto.completed());
    }

    @Test
    void create_WithNullComparison_ShouldCreateDTO() {
        // Given
        String status = null;
        boolean completed = status != null;

        // When
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(completed);

        // Then
        assertNotNull(dto);
        assertFalse(dto.completed());
    }

    @Test
    void create_WithNotNullComparison_ShouldCreateDTO() {
        // Given
        String status = "ACTIVE";
        boolean completed = status != null;

        // When
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(completed);

        // Then
        assertNotNull(dto);
        assertTrue(dto.completed());
    }

    @Test
    void create_WithArrayCheck_ShouldCreateDTO() {
        // Given
        String[] statuses = {"PENDING", "IN_PROGRESS", "COMPLETED"};
        boolean completed = statuses.length > 0;

        // When
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(completed);

        // Then
        assertNotNull(dto);
        assertTrue(dto.completed());
    }

    @Test
    void create_WithEmptyArrayCheck_ShouldCreateDTO() {
        // Given
        String[] statuses = {};
        boolean completed = statuses.length > 0;

        // When
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(completed);

        // Then
        assertNotNull(dto);
        assertFalse(dto.completed());
    }

    @Test
    void create_WithNumberComparison_ShouldCreateDTO() {
        // Given
        int priority = 5;
        boolean completed = priority >= 5;

        // When
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(completed);

        // Then
        assertNotNull(dto);
        assertTrue(dto.completed());
    }

    @Test
    void create_WithNumberComparisonFalse_ShouldCreateDTO() {
        // Given
        int priority = 3;
        boolean completed = priority >= 5;

        // When
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(completed);

        // Then
        assertNotNull(dto);
        assertFalse(dto.completed());
    }

    @Test
    void create_WithLogicalAnd_ShouldCreateDTO() {
        // Given
        boolean hasTitle = true;
        boolean hasDescription = true;
        boolean completed = hasTitle && hasDescription;

        // When
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(completed);

        // Then
        assertNotNull(dto);
        assertTrue(dto.completed());
    }

    @Test
    void create_WithLogicalAndFalse_ShouldCreateDTO() {
        // Given
        boolean hasTitle = true;
        boolean hasDescription = false;
        boolean completed = hasTitle && hasDescription;

        // When
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(completed);

        // Then
        assertNotNull(dto);
        assertFalse(dto.completed());
    }

    @Test
    void create_WithLogicalOr_ShouldCreateDTO() {
        // Given
        boolean isUrgent = false;
        boolean isImportant = true;
        boolean completed = isUrgent || isImportant;

        // When
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(completed);

        // Then
        assertNotNull(dto);
        assertTrue(dto.completed());
    }

    @Test
    void create_WithLogicalOrFalse_ShouldCreateDTO() {
        // Given
        boolean isUrgent = false;
        boolean isImportant = false;
        boolean completed = isUrgent || isImportant;

        // When
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(completed);

        // Then
        assertNotNull(dto);
        assertFalse(dto.completed());
    }

    @Test
    void create_WithLogicalNot_ShouldCreateDTO() {
        // Given
        boolean isPending = true;
        boolean completed = !isPending;

        // When
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(completed);

        // Then
        assertNotNull(dto);
        assertFalse(dto.completed());
    }

    @Test
    void create_WithLogicalNotFalse_ShouldCreateDTO() {
        // Given
        boolean isPending = false;
        boolean completed = !isPending;

        // When
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(completed);

        // Then
        assertNotNull(dto);
        assertTrue(dto.completed());
    }

    @Test
    void create_WithComplexBooleanExpression_ShouldCreateDTO() {
        // Given
        int priority = 8;
        String status = "IN_PROGRESS";
        boolean completed = priority > 5 && "COMPLETED".equals(status);

        // When
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(completed);

        // Then
        assertNotNull(dto);
        assertFalse(dto.completed());
    }

    @Test
    void create_WithComplexBooleanExpressionTrue_ShouldCreateDTO() {
        // Given
        int priority = 8;
        String status = "COMPLETED";
        boolean completed = priority > 5 && "COMPLETED".equals(status);

        // When
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(completed);

        // Then
        assertNotNull(dto);
        assertTrue(dto.completed());
    }

    @Test
    void create_WithTernaryOperator_ShouldCreateDTO() {
        // Given
        int priority = 10;
        boolean completed = priority > 5 ? true : false;

        // When
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(completed);

        // Then
        assertNotNull(dto);
        assertTrue(dto.completed());
    }

    @Test
    void create_WithTernaryOperatorFalse_ShouldCreateDTO() {
        // Given
        int priority = 3;
        boolean completed = priority > 5 ? true : false;

        // When
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(completed);

        // Then
        assertNotNull(dto);
        assertFalse(dto.completed());
    }

    @Test
    void create_WithMethodReturn_ShouldCreateDTO() {
        // Given
        boolean completed = isHighPriority(8);

        // When
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(completed);

        // Then
        assertNotNull(dto);
        assertTrue(dto.completed());
    }

    @Test
    void create_WithMethodReturnFalse_ShouldCreateDTO() {
        // Given
        boolean completed = isHighPriority(3);

        // When
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(completed);

        // Then
        assertNotNull(dto);
        assertFalse(dto.completed());
    }

    @Test
    void create_WithStaticFinal_ShouldCreateDTO() {
        // Given
        boolean completed = true;

        // When
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(completed);

        // Then
        assertNotNull(dto);
        assertTrue(dto.completed());
    }

    @Test
    void create_WithStaticFinalFalse_ShouldCreateDTO() {
        // Given
        boolean completed = false;

        // When
        TaskStatusUpdateDTO dto = new TaskStatusUpdateDTO(completed);

        // Then
        assertNotNull(dto);
        assertFalse(dto.completed());
    }

    // Helper method for testing
    private boolean isHighPriority(int priority) {
        return priority > 5;
    }
} 