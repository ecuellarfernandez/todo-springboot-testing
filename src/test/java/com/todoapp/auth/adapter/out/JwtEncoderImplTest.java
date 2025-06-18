package com.todoapp.auth.adapter.out;

import com.todoapp.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class JwtEncoderImplTest {

    private JwtEncoderImpl jwtEncoder;

    @BeforeEach
    void setUp() {
        jwtEncoder = new JwtEncoderImpl();
        ReflectionTestUtils.setField(jwtEncoder, "secretKey", "miSuperSecretoUltraLargo123@@12345678");
    }

    @Test
    void shouldGenerateAndValidateToken() {
        User user = new User(UUID.randomUUID(), "user", "name", "mail", "pass");
        String token = jwtEncoder.generateToken(user);
        assertThat(token).isNotBlank();
        assertThat(jwtEncoder.validateToken(token)).isTrue();
    }

    @Test
    void shouldExtractUsername() {
        User user = new User(UUID.randomUUID(), "user", "name", "mail", "pass");
        String token = jwtEncoder.generateToken(user);
        String username = jwtEncoder.extractUsername(token);
        assertThat(username).isEqualTo("mail");
    }

    @Test
    void shouldReturnFalseForInvalidToken() {
        assertThat(jwtEncoder.validateToken("invalid.token.here")).isFalse();
    }
} 