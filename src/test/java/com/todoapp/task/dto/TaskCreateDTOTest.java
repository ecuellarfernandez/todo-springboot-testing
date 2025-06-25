package com.todoapp.task.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

class TaskCreateDTOTest {

    @Test
    void shouldStoreValues() {
        UUID todoListId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        LocalDate dueDate = LocalDate.now().plusDays(7);
        
        TaskCreateDTO dto = new TaskCreateDTO("Test Task", "Test Description", dueDate, todoListId, projectId);
        
        assertThat(dto.title()).isEqualTo("Test Task");
        assertThat(dto.description()).isEqualTo("Test Description");
        assertThat(dto.dueDate()).isEqualTo(dueDate);
        assertThat(dto.todoListId()).isEqualTo(todoListId);
        assertThat(dto.projectId()).isEqualTo(projectId);
    }

    @Test
    void shouldBeImmutable() {
        UUID todoListId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        LocalDate dueDate = LocalDate.now().plusDays(7);
        
        TaskCreateDTO dto1 = new TaskCreateDTO("Test Task", "Test Description", dueDate, todoListId, projectId);
        TaskCreateDTO dto2 = new TaskCreateDTO("Test Task", "Test Description", dueDate, todoListId, projectId);
        
        assertThat(dto1).isEqualTo(dto2);
    }

    @Test
    void shouldHandleNullDescription() {
        UUID todoListId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        LocalDate dueDate = LocalDate.now().plusDays(7);
        
        TaskCreateDTO dto = new TaskCreateDTO("Test Task", null, dueDate, todoListId, projectId);
        
        assertThat(dto.title()).isEqualTo("Test Task");
        assertThat(dto.description()).isNull();
        assertThat(dto.dueDate()).isEqualTo(dueDate);
        assertThat(dto.todoListId()).isEqualTo(todoListId);
        assertThat(dto.projectId()).isEqualTo(projectId);
    }

    @Test
    void shouldHandleNullDueDate() {
        UUID todoListId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        
        TaskCreateDTO dto = new TaskCreateDTO("Test Task", "Test Description", null, todoListId, projectId);
        
        assertThat(dto.title()).isEqualTo("Test Task");
        assertThat(dto.description()).isEqualTo("Test Description");
        assertThat(dto.dueDate()).isNull();
        assertThat(dto.todoListId()).isEqualTo(todoListId);
        assertThat(dto.projectId()).isEqualTo(projectId);
    }

    @Test
    void shouldFailValidationOnEmptyTitle() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        UUID todoListId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        LocalDate dueDate = LocalDate.now().plusDays(7);
        
        TaskCreateDTO dto = new TaskCreateDTO("", "Test Description", dueDate, todoListId, projectId);

        Set<ConstraintViolation<TaskCreateDTO>> violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> 
            violation.getPropertyPath().toString().equals("title"));
    }

    @Test
    void shouldFailValidationOnBlankTitle() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        UUID todoListId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        LocalDate dueDate = LocalDate.now().plusDays(7);
        
        TaskCreateDTO dto = new TaskCreateDTO("   ", "Test Description", dueDate, todoListId, projectId);

        Set<ConstraintViolation<TaskCreateDTO>> violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> 
            violation.getPropertyPath().toString().equals("title"));
    }

    @Test
    void shouldFailValidationOnNullTodoListId() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        UUID projectId = UUID.randomUUID();
        LocalDate dueDate = LocalDate.now().plusDays(7);
        
        TaskCreateDTO dto = new TaskCreateDTO("Test Task", "Test Description", dueDate, null, projectId);

        Set<ConstraintViolation<TaskCreateDTO>> violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> 
            violation.getPropertyPath().toString().equals("todoListId"));
    }

    @Test
    void shouldFailValidationOnNullProjectId() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        UUID todoListId = UUID.randomUUID();
        LocalDate dueDate = LocalDate.now().plusDays(7);
        
        TaskCreateDTO dto = new TaskCreateDTO("Test Task", "Test Description", dueDate, todoListId, null);

        Set<ConstraintViolation<TaskCreateDTO>> violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> 
            violation.getPropertyPath().toString().equals("projectId"));
    }

    @Test
    void shouldPassValidationWithValidData() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        UUID todoListId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        LocalDate dueDate = LocalDate.now().plusDays(7);
        
        TaskCreateDTO dto = new TaskCreateDTO("Valid Task", "Valid Description", dueDate, todoListId, projectId);

        Set<ConstraintViolation<TaskCreateDTO>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldPassValidationWithNullDescription() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        UUID todoListId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        LocalDate dueDate = LocalDate.now().plusDays(7);
        
        TaskCreateDTO dto = new TaskCreateDTO("Valid Task", null, dueDate, todoListId, projectId);

        Set<ConstraintViolation<TaskCreateDTO>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldPassValidationWithNullDueDate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        UUID todoListId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        
        TaskCreateDTO dto = new TaskCreateDTO("Valid Task", "Valid Description", null, todoListId, projectId);

        Set<ConstraintViolation<TaskCreateDTO>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldHandlePastDueDate() {
        UUID todoListId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        LocalDate pastDate = LocalDate.now().minusDays(1);
        
        TaskCreateDTO dto = new TaskCreateDTO("Test Task", "Test Description", pastDate, todoListId, projectId);
        
        assertThat(dto.dueDate()).isEqualTo(pastDate);
    }

    @Test
    void shouldHandleFutureDueDate() {
        UUID todoListId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        LocalDate futureDate = LocalDate.now().plusDays(30);
        
        TaskCreateDTO dto = new TaskCreateDTO("Test Task", "Test Description", futureDate, todoListId, projectId);
        
        assertThat(dto.dueDate()).isEqualTo(futureDate);
    }

    @Test
    void shouldHandleTodayDueDate() {
        UUID todoListId = UUID.randomUUID();
        UUID projectId = UUID.randomUUID();
        LocalDate today = LocalDate.now();
        
        TaskCreateDTO dto = new TaskCreateDTO("Test Task", "Test Description", today, todoListId, projectId);
        
        assertThat(dto.dueDate()).isEqualTo(today);
    }
} 