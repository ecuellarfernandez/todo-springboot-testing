package com.todoapp.auth.adapter.in;
import com.todoapp.auth.dto.LoginRequestDTO;
import com.todoapp.auth.dto.AuthResponseDTO;
import com.todoapp.auth.dto.UserMeResponseDTO;
import com.todoapp.auth.port.in.LoginUseCase;
import com.todoapp.auth.port.in.UserContextUseCase;
import com.todoapp.user.domain.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final UserContextUseCase userContextUseCase;

    public AuthController(LoginUseCase loginUseCase, UserContextUseCase userContextUseCase) {
        this.loginUseCase = loginUseCase;
        this.userContextUseCase = userContextUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(loginUseCase.login(dto));
    }

    @GetMapping("/me")
    public ResponseEntity<UserMeResponseDTO> me(@RequestHeader("Authorization") String authHeader) {
        UserMeResponseDTO userResponse = userContextUseCase.getCurrentUserInfo(authHeader);
        return ResponseEntity.ok(userResponse);
    }
}