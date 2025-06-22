package com.todoapp.user.port.out;

import com.todoapp.user.domain.User;
import java.util.UUID;

public interface UserRepository {
    User save(User user);
    User findById(UUID id);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
