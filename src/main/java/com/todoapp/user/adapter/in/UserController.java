package com.todoapp.user.adapter.in;

import com.todoapp.user.dto.UserRegisterRequest;
import com.todoapp.user.dto.UserRegisterResponse;
import com.todoapp.user.domain.User;
import com.todoapp.user.port.in.UserUseCase;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @PostMapping("/register")
    public UserRegisterResponse register(@RequestBody @Valid UserRegisterRequest request) {
        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPasswordHash(request.password());
        user.setCreatedAt(LocalDateTime.now());

        String token = userUseCase.registerUser(user);

        return new UserRegisterResponse(token);
    }
}
