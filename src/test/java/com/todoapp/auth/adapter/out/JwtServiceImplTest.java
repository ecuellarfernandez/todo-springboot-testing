package com.todoapp.auth.adapter.out;

import com.todoapp.user.domain.User;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import static org.assertj.core.api.Assertions.assertThat;

class JwtServiceImplTest {

    JwtServiceImpl service;

    @BeforeEach
    void setUp() {
        String secret = "12345678901234567890123456789012";
        SecretKey key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        JwtEncoder encoder = new NimbusJwtEncoder(new ImmutableSecret<>(key));
        JwtDecoder decoder = NimbusJwtDecoder.withSecretKey(key).build();
        service = new JwtServiceImpl(encoder, decoder);
    }

    @Test
    void shouldGenerateValidToken() {
        User user = new User(1L, "user", "name", "mail", "pass");
        String token = service.generateToken(user);
        assertThat(service.isValid(token)).isTrue();
        assertThat(service.extractUsername(token)).isEqualTo("mail");
        assertThat(service.extractEmail(token)).isEqualTo("mail");
    }

    @Test
    void shouldDetectInvalidToken() {
        assertThat(service.isValid("bad.token.value")).isFalse();
    }
}
