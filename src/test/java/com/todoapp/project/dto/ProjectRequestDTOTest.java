package com.todoapp.project.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Set;

class ProjectRequestDTOTest {

    @Test
    void shouldStoreValues() {
        ProjectRequestDTO dto = new ProjectRequestDTO("Test Project", "Test Description");
        assertThat(dto.name()).isEqualTo("Test Project");
        assertThat(dto.description()).isEqualTo("Test Description");
    }

    @Test
    void shouldBeImmutable() {
        ProjectRequestDTO dto1 = new ProjectRequestDTO("Project A", "Description A");
        ProjectRequestDTO dto2 = new ProjectRequestDTO("Project A", "Description A");
        assertThat(dto1).isEqualTo(dto2);
    }

    @Test
    void shouldHandleNullDescription() {
        ProjectRequestDTO dto = new ProjectRequestDTO("Test Project", null);
        assertThat(dto.name()).isEqualTo("Test Project");
        assertThat(dto.description()).isNull();
    }

    @Test
    void shouldFailValidationOnEmptyName() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        ProjectRequestDTO dto = new ProjectRequestDTO("", "Test Description");

        Set<ConstraintViolation<ProjectRequestDTO>> violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> 
            violation.getPropertyPath().toString().equals("name"));
    }

    @Test
    void shouldFailValidationOnBlankName() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        ProjectRequestDTO dto = new ProjectRequestDTO("   ", "Test Description");

        Set<ConstraintViolation<ProjectRequestDTO>> violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> 
            violation.getPropertyPath().toString().equals("name"));
    }

    @Test
    void shouldPassValidationWithValidData() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        ProjectRequestDTO dto = new ProjectRequestDTO("Valid Project", "Valid Description");

        Set<ConstraintViolation<ProjectRequestDTO>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }
} 