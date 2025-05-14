package com.todoapp.auth.port.out;

import com.todoapp.user.domain.User;

public interface UserCredentialsPort {
    User findByEmail(String email);
}