package com.todoapp.user.adapter.out;

import com.todoapp.user.domain.User;
import com.todoapp.user.port.out.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpa;

    public UserRepositoryImpl(UserJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public User save(User user) {
        UserEntity entity = new UserEntity();
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setPasswordHash(user.getPasswordHash());
        entity.setCreatedAt(user.getCreatedAt());

        UserEntity saved = jpa.save(entity);

        User newUser = new User();
        newUser.setUserId(saved.getUserId());
        newUser.setUsername(saved.getUsername());
        newUser.setEmail(saved.getEmail());
        newUser.setPasswordHash(saved.getPasswordHash());
        newUser.setCreatedAt(saved.getCreatedAt());

        return newUser;
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpa.existsByEmail(email);
    }
}
