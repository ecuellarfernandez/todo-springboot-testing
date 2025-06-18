package com.todoapp.user.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
}
