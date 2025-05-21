package com.todoapp.auth.adapter.out;

import com.todoapp.auth.port.out.JwtService;
import com.todoapp.user.domain.User;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtServiceImpl implements JwtService {

    private final JwtEncoder encoder;
    private final JwtDecoder decoder;

    public JwtServiceImpl(JwtEncoder encoder, JwtDecoder decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }

    @Override
    public String generateToken(User user) {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(user.getEmail())
                .claim("username", user.getUsername())
                .claim("email", user.getEmail())
                .claim("userId", user.getId())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(10, ChronoUnit.HOURS))
                .build();
        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @Override
    public boolean isValid(String token) {
        try {
            decoder.decode(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    @Override
    public String extractUsername(String token) {
        return decoder.decode(token).getSubject();
    }

    @Override
    public String extractEmail(String token) {
        return decoder.decode(token).getClaim("email");
    }
}
