package com.todoapp.auth.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Set;

class LoginRequestDTOTest {

    @Test
    void shouldStoreValues() {
        LoginRequestDTO dto = new LoginRequestDTO("test@example.com", "pass");
        assertThat(dto.email()).isEqualTo("test@example.com");
        assertThat(dto.password()).isEqualTo("pass");
    }

    @Test
    void shouldBeImmutable() {
        LoginRequestDTO dto1 = new LoginRequestDTO("a", "b");
        LoginRequestDTO dto2 = new LoginRequestDTO("a", "b");
        assertThat(dto1).isEqualTo(dto2);
    }

    @Test
    void shouldFailValidationOnInvalidEmailOrPassword() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        LoginRequestDTO dto1 = new LoginRequestDTO("noemail", "pass");
        LoginRequestDTO dto2 = new LoginRequestDTO("", "");

        Set<ConstraintViolation<LoginRequestDTO>> violations1 = validator.validate(dto1);
        Set<ConstraintViolation<LoginRequestDTO>> violations2 = validator.validate(dto2);

        assertThat(violations1).isNotEmpty();
        assertThat(violations2).isNotEmpty();
    }
}
