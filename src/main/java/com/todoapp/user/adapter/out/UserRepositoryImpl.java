package com.todoapp.user.adapter.out;

import com.todoapp.user.domain.User;
import com.todoapp.user.port.out.UserRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpa;
    private final UserMapper mapper;

    public UserRepositoryImpl(UserJpaRepository jpa, UserMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public User save(User user) {
        UserEntity entity = mapper.domainToEntity(user);
        UserEntity savedEntity = jpa.save(entity);
        return mapper.entityToDomain(savedEntity);
    }

    @Override
    public User findById(UUID id) {
        UserEntity entity = jpa.findById(id).orElseThrow();
        return mapper.entityToDomain(entity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpa.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpa.existsByUsername(username);
    }
}
