package com.todoapp.auth.adapter.in;
import com.todoapp.auth.dto.LoginRequestDTO;
import com.todoapp.auth.dto.AuthResponseDTO;
import com.todoapp.auth.port.in.AuthUseCase;
import com.todoapp.user.domain.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    private final AuthUseCase authUseCase;

    public AuthController(AuthUseCase authUseCase) {
        this.authUseCase = authUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(authUseCase.login(dto));
    }

    @GetMapping("/me")
    public ResponseEntity<User> me(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        User user = authUseCase.getCurrentUser(token);
        return ResponseEntity.ok(user);
    }
}