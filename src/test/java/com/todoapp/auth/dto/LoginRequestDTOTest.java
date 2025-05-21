package com.todoapp.auth.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

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
}
