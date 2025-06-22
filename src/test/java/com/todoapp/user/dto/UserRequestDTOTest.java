package com.todoapp.user.dto;

import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class UserRequestDTOTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void shouldCreateValidUserRequestDTO() {
        UserRequestDTO dto = new UserRequestDTO("testuser", "Test User", "test@email.com", "123456");
        
        assertThat(dto.username()).isEqualTo("testuser");
        assertThat(dto.name()).isEqualTo("Test User");
        assertThat(dto.email()).isEqualTo("test@email.com");
        assertThat(dto.password()).isEqualTo("123456");
    }

    @Test
    void shouldBeImmutable() {
        UserRequestDTO dto1 = new UserRequestDTO("user", "name", "email@test.com", "pass");
        UserRequestDTO dto2 = new UserRequestDTO("user", "name", "email@test.com", "pass");
        
        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    void shouldFailValidationOnEmptyUsername() {
        UserRequestDTO dto = new UserRequestDTO("", "Test User", "test@email.com", "123456");
        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(dto);
        
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> 
            violation.getPropertyPath().toString().equals("username") &&
            violation.getMessage().contains("obligatorio")
        );
    }

    @Test
    void shouldFailValidationOnShortUsername() {
        UserRequestDTO dto = new UserRequestDTO("ab", "Test User", "test@email.com", "123456");
        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(dto);
        
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> 
            violation.getPropertyPath().toString().equals("username") &&
            violation.getMessage().contains("3 caracteres")
        );
    }

    @Test
    void shouldFailValidationOnEmptyName() {
        UserRequestDTO dto = new UserRequestDTO("testuser", "", "test@email.com", "123456");
        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(dto);
        
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> 
            violation.getPropertyPath().toString().equals("name") &&
            violation.getMessage().contains("obligatorio")
        );
    }

    @Test
    void shouldFailValidationOnShortName() {
        UserRequestDTO dto = new UserRequestDTO("testuser", "ab", "test@email.com", "123456");
        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(dto);
        
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> 
            violation.getPropertyPath().toString().equals("name") &&
            violation.getMessage().contains("3 caracteres")
        );
    }

    @Test
    void shouldFailValidationOnEmptyEmail() {
        UserRequestDTO dto = new UserRequestDTO("testuser", "Test User", "", "123456");
        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(dto);
        
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> 
            violation.getPropertyPath().toString().equals("email") &&
            violation.getMessage().contains("obligatorio")
        );
    }

    @Test
    void shouldFailValidationOnInvalidEmailFormat() {
        UserRequestDTO dto = new UserRequestDTO("testuser", "Test User", "invalid-email", "123456");
        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(dto);
        
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> 
            violation.getPropertyPath().toString().equals("email") &&
            violation.getMessage().contains("válido")
        );
    }

    @Test
    void shouldFailValidationOnEmptyPassword() {
        UserRequestDTO dto = new UserRequestDTO("testuser", "Test User", "test@email.com", "");
        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(dto);
        
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> 
            violation.getPropertyPath().toString().equals("password") &&
            violation.getMessage().contains("obligatoria")
        );
    }

    @Test
    void shouldFailValidationOnShortPassword() {
        UserRequestDTO dto = new UserRequestDTO("testuser", "Test User", "test@email.com", "123");
        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(dto);
        
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> 
            violation.getPropertyPath().toString().equals("password") &&
            violation.getMessage().contains("6 caracteres")
        );
    }

    @Test
    void shouldPassValidationWithValidData() {
        UserRequestDTO dto = new UserRequestDTO("testuser", "Test User", "test@email.com", "123456");
        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(dto);
        
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldPassValidationWithMinimumValidData() {
        UserRequestDTO dto = new UserRequestDTO("abc", "abc", "test@email.com", "123456");
        Set<ConstraintViolation<UserRequestDTO>> violations = validator.validate(dto);
        
        assertThat(violations).isEmpty();
    }
} 