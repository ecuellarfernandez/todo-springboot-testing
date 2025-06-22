package com.todoapp.user.adapter.out;

import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class UserEntityTest {

    @Test
    void shouldCreateUserEntityWithConstructor() {
        // Given
        String username = "testuser";
        String name = "Test User";
        String email = "test@email.com";
        String password = "hashedPassword";

        // When
        UserEntity entity = new UserEntity(username, name, email, password);

        // Then
        assertThat(entity.getUsername()).isEqualTo(username);
        assertThat(entity.getName()).isEqualTo(name);
        assertThat(entity.getEmail()).isEqualTo(email);
        assertThat(entity.getPassword()).isEqualTo(password);
        assertThat(entity.getId()).isNull(); // ID se genera automáticamente
    }

    @Test
    void shouldCreateUserEntityWithDefaultConstructor() {
        // When
        UserEntity entity = new UserEntity();

        // Then
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isNull();
        assertThat(entity.getUsername()).isNull();
        assertThat(entity.getName()).isNull();
        assertThat(entity.getEmail()).isNull();
        assertThat(entity.getPassword()).isNull();
    }

    @Test
    void shouldSetAndGetId() {
        // Given
        UserEntity entity = new UserEntity();
        UUID userId = UUID.randomUUID();

        // When
        entity.setId(userId);

        // Then
        assertThat(entity.getId()).isEqualTo(userId);
    }

    @Test
    void shouldSetAndGetUsername() {
        // Given
        UserEntity entity = new UserEntity();
        String username = "testuser";

        // When
        entity.setUsername(username);

        // Then
        assertThat(entity.getUsername()).isEqualTo(username);
    }

    @Test
    void shouldSetAndGetName() {
        // Given
        UserEntity entity = new UserEntity();
        String name = "Test User";

        // When
        entity.setName(name);

        // Then
        assertThat(entity.getName()).isEqualTo(name);
    }

    @Test
    void shouldSetAndGetEmail() {
        // Given
        UserEntity entity = new UserEntity();
        String email = "test@email.com";

        // When
        entity.setEmail(email);

        // Then
        assertThat(entity.getEmail()).isEqualTo(email);
    }

    @Test
    void shouldSetAndGetPassword() {
        // Given
        UserEntity entity = new UserEntity();
        String password = "hashedPassword";

        // When
        entity.setPassword(password);

        // Then
        assertThat(entity.getPassword()).isEqualTo(password);
    }

    @Test
    void shouldHandleNullValues() {
        // Given
        UserEntity entity = new UserEntity();

        // When
        entity.setId(null);
        entity.setUsername(null);
        entity.setName(null);
        entity.setEmail(null);
        entity.setPassword(null);

        // Then
        assertThat(entity.getId()).isNull();
        assertThat(entity.getUsername()).isNull();
        assertThat(entity.getName()).isNull();
        assertThat(entity.getEmail()).isNull();
        assertThat(entity.getPassword()).isNull();
    }

    @Test
    void shouldHandleEmptyStrings() {
        // Given
        UserEntity entity = new UserEntity();

        // When
        entity.setUsername("");
        entity.setName("");
        entity.setEmail("");
        entity.setPassword("");

        // Then
        assertThat(entity.getUsername()).isEmpty();
        assertThat(entity.getName()).isEmpty();
        assertThat(entity.getEmail()).isEmpty();
        assertThat(entity.getPassword()).isEmpty();
    }
} 