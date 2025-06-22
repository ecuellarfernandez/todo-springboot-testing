package com.todoapp.auth.port.in;

import com.todoapp.user.domain.User;

public interface UserContextUseCase {
    User getCurrentUser(String token);
}
