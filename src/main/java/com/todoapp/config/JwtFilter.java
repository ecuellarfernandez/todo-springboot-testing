package com.todoapp.config;

import com.todoapp.auth.port.out.JwtEncoder;
import com.todoapp.user.adapter.out.UserJpaRepository;
import com.todoapp.user.adapter.out.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private static final org.apache.commons.logging.Log logger = org.apache.commons.logging.LogFactory.getLog(JwtFilter.class);
    private final JwtEncoder jwtEncoder;
    private final UserJpaRepository userRepo;

    public JwtFilter(JwtEncoder jwtEncoder, UserJpaRepository userRepo) {
        this.jwtEncoder = jwtEncoder;
        this.userRepo = userRepo;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                   @NonNull HttpServletResponse response,
                                   @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring(7);
        boolean authenticationSuccessful = false;

        try {
            String username = jwtEncoder.extractUsername(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserEntity userEntity = userRepo.findByEmail(username).orElse(null);
                if (userEntity != null && jwtEncoder.validateToken(jwt)) {
                    UsernamePasswordAuthenticationToken authToken = getUsernamePasswordAuthenticationToken(username, userEntity);

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    authenticationSuccessful = true;
                }
            }
        } catch (Exception e) {
            logger.error("Error al procesar el token JWT: {}", e);
            SecurityContextHolder.clearContext();
            request.setAttribute("jwt_error", e);
        }

        if (!authenticationSuccessful) {
            logger.debug("No se estableció autenticación para esta solicitud");
        }

        filterChain.doFilter(request, response);
    }

    private static UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String username, UserEntity userEntity) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                username,
                null,
                Collections.emptyList()
        );

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userId", userEntity.getId());
        userInfo.put("email", userEntity.getEmail());

        authToken.setDetails(userInfo);
        return authToken;
    }
}