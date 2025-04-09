package com.todoapp.user.port.out;

import com.todoapp.user.domain.User;

public interface UserRepository {
    User save(User user);
    boolean existsByEmail(String email);
}
