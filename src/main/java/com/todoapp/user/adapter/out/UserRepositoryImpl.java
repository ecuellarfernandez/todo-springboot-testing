package com.todoapp.user.adapter.out;

import com.todoapp.user.domain.User;
import com.todoapp.user.port.out.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpa;

    public UserRepositoryImpl(UserJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public User save(User user) {
        UserEntity entity = new UserEntity();
        entity.name = user.getName();
        entity.username = user.getUsername();
        entity.email = user.getEmail();
        entity.password = user.getPassword();
        entity = jpa.save(entity);
        return new User(entity.id, entity.name, entity.username, entity.email, entity.password);
    }

    @Override
    public User findById(Long id) {
        UserEntity entity = jpa.findById(id).orElseThrow();
        return new User(entity.id, entity.name, entity.username, entity.email, entity.password);
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
