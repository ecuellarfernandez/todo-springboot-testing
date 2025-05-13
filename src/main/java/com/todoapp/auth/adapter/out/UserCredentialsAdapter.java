package com.todoapp.auth.adapter.out;

import com.todoapp.auth.port.out.UserCredentialsPort;
import com.todoapp.user.domain.User;
import com.todoapp.user.adapter.out.UserJpaRepository;
import com.todoapp.user.adapter.out.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserCredentialsAdapter implements UserCredentialsPort {

    private final UserJpaRepository jpa;

    public UserCredentialsAdapter(UserJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public User findByEmail(String email) {
        UserEntity entity = jpa.findByEmail(email).orElseThrow();
        return new User(entity.id, entity.username, entity.email, entity.password);
    }
}