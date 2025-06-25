package com.todoapp.task.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.time.LocalDate;
import java.util.Set;

class TaskRequestDTOTest {

    @Test
    void shouldStoreValues() {
        LocalDate dueDate = LocalDate.now().plusDays(7);
        TaskRequestDTO dto = new TaskRequestDTO("Test Task", "Test Description", dueDate);
        
        assertThat(dto.title()).isEqualTo("Test Task");
        assertThat(dto.description()).isEqualTo("Test Description");
        assertThat(dto.dueDate()).isEqualTo(dueDate);
    }

    @Test
    void shouldBeImmutable() {
        LocalDate dueDate = LocalDate.now().plusDays(7);
        TaskRequestDTO dto1 = new TaskRequestDTO("Task A", "Description A", dueDate);
        TaskRequestDTO dto2 = new TaskRequestDTO("Task A", "Description A", dueDate);
        
        assertThat(dto1).isEqualTo(dto2);
    }

    @Test
    void shouldHandleNullDescription() {
        LocalDate dueDate = LocalDate.now().plusDays(7);
        TaskRequestDTO dto = new TaskRequestDTO("Test Task", null, dueDate);
        
        assertThat(dto.title()).isEqualTo("Test Task");
        assertThat(dto.description()).isNull();
        assertThat(dto.dueDate()).isEqualTo(dueDate);
    }

    @Test
    void shouldHandleNullDueDate() {
        TaskRequestDTO dto = new TaskRequestDTO("Test Task", "Test Description", null);
        
        assertThat(dto.title()).isEqualTo("Test Task");
        assertThat(dto.description()).isEqualTo("Test Description");
        assertThat(dto.dueDate()).isNull();
    }

    @Test
    void shouldFailValidationOnEmptyTitle() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        LocalDate dueDate = LocalDate.now().plusDays(7);
        TaskRequestDTO dto = new TaskRequestDTO("", "Test Description", dueDate);

        Set<ConstraintViolation<TaskRequestDTO>> violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> 
            violation.getPropertyPath().toString().equals("title"));
    }

    @Test
    void shouldFailValidationOnBlankTitle() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        LocalDate dueDate = LocalDate.now().plusDays(7);
        TaskRequestDTO dto = new TaskRequestDTO("   ", "Test Description", dueDate);

        Set<ConstraintViolation<TaskRequestDTO>> violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> 
            violation.getPropertyPath().toString().equals("title"));
    }

    @Test
    void shouldPassValidationWithValidData() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        LocalDate dueDate = LocalDate.now().plusDays(7);
        TaskRequestDTO dto = new TaskRequestDTO("Valid Task", "Valid Description", dueDate);

        Set<ConstraintViolation<TaskRequestDTO>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldPassValidationWithNullDescription() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        LocalDate dueDate = LocalDate.now().plusDays(7);
        TaskRequestDTO dto = new TaskRequestDTO("Valid Task", null, dueDate);

        Set<ConstraintViolation<TaskRequestDTO>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldPassValidationWithNullDueDate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        TaskRequestDTO dto = new TaskRequestDTO("Valid Task", "Valid Description", null);

        Set<ConstraintViolation<TaskRequestDTO>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }
} 