package com.todoapp.user.port.out;

import com.todoapp.user.domain.User;

public interface UserRepository {
    User save(User user);
    User findById(Long id);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}