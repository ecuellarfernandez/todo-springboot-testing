package com.todoapp.user.adapter.out;

import com.todoapp.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    void shouldMapEntityToDomain() {
        // Given
        UUID userId = UUID.randomUUID();
        UserEntity entity = new UserEntity("testuser", "Test User", "test@email.com", "hashedPassword");
        entity.setId(userId);

        // When
        User result = userMapper.entityToDomain(entity);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getUsername()).isEqualTo("testuser");
        assertThat(result.getName()).isEqualTo("Test User");
        assertThat(result.getEmail()).isEqualTo("test@email.com");
        assertThat(result.getPassword()).isEqualTo("hashedPassword");
    }

    @Test
    void shouldMapDomainToEntity() {
        // Given
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "testuser", "Test User", "test@email.com", "hashedPassword");

        // When
        UserEntity result = userMapper.domainToEntity(user);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getUsername()).isEqualTo("testuser");
        assertThat(result.getName()).isEqualTo("Test User");
        assertThat(result.getEmail()).isEqualTo("test@email.com");
        assertThat(result.getPassword()).isEqualTo("hashedPassword");
    }

    @Test
    void shouldMapListOfEntitiesToDomains() {
        // Given
        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();
        
        UserEntity entity1 = new UserEntity("user1", "User One", "user1@email.com", "pass1");
        UserEntity entity2 = new UserEntity("user2", "User Two", "user2@email.com", "pass2");
        entity1.setId(userId1);
        entity2.setId(userId2);
        
        List<UserEntity> entities = Arrays.asList(entity1, entity2);

        // When
        List<User> result = userMapper.entitiesToDomains(entities);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo(userId1);
        assertThat(result.get(0).getUsername()).isEqualTo("user1");
        assertThat(result.get(1).getId()).isEqualTo(userId2);
        assertThat(result.get(1).getUsername()).isEqualTo("user2");
    }

    @Test
    void shouldHandleNullValuesInEntityToDomain() {
        // Given
        UserEntity entity = new UserEntity();
        entity.setId(null);
        entity.setUsername(null);
        entity.setName(null);
        entity.setEmail(null);
        entity.setPassword(null);

        // When
        User result = userMapper.entityToDomain(entity);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNull();
        assertThat(result.getUsername()).isNull();
        assertThat(result.getName()).isNull();
        assertThat(result.getEmail()).isNull();
        assertThat(result.getPassword()).isNull();
    }

    @Test
    void shouldHandleNullValuesInDomainToEntity() {
        // Given
        User user = new User(null, null, null, null, null);

        // When
        UserEntity result = userMapper.domainToEntity(user);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNull();
        assertThat(result.getUsername()).isNull();
        assertThat(result.getName()).isNull();
        assertThat(result.getEmail()).isNull();
        assertThat(result.getPassword()).isNull();
    }

    @Test
    void shouldHandleEmptyStrings() {
        // Given
        UserEntity entity = new UserEntity("", "", "", "");

        // When
        User result = userMapper.entityToDomain(entity);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEmpty();
        assertThat(result.getName()).isEmpty();
        assertThat(result.getEmail()).isEmpty();
        assertThat(result.getPassword()).isEmpty();
    }

    @Test
    void shouldHandleEmptyList() {
        // Given
        List<UserEntity> entities = Arrays.asList();

        // When
        List<User> result = userMapper.entitiesToDomains(entities);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void shouldHandleNullList() {
        // Given
        List<UserEntity> entities = null;

        // When
        List<User> result = userMapper.entitiesToDomains(entities);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void shouldMapWithPartialData() {
        // Given
        UserEntity entity = new UserEntity("testuser", null, "test@email.com", null);
        entity.setId(UUID.randomUUID());

        // When
        User result = userMapper.entityToDomain(entity);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(entity.getId());
        assertThat(result.getUsername()).isEqualTo("testuser");
        assertThat(result.getName()).isNull();
        assertThat(result.getEmail()).isEqualTo("test@email.com");
        assertThat(result.getPassword()).isNull();
    }
} 