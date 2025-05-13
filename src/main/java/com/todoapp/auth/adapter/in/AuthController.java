package com.todoapp.auth.adapter.in;

import com.todoapp.auth.dto.LoginRequestDTO;
import com.todoapp.auth.dto.AuthResponseDTO;
import com.todoapp.auth.port.in.AuthUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthUseCase authUseCase;

    public AuthController(AuthUseCase authUseCase) {
        this.authUseCase = authUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(authUseCase.login(dto));
    }
}