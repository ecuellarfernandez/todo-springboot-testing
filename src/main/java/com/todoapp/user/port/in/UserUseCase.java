package com.todoapp.user.port.in;

import com.todoapp.user.domain.User;

public interface UserUseCase {
    String registerUser(User user);
}
