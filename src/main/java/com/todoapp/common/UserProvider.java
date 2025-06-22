package com.todoapp.common;

import com.todoapp.auth.port.in.UserContextUseCase;
import com.todoapp.user.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class UserProvider {
    private final UserContextUseCase userContext;

    public UserProvider(UserContextUseCase userContext) {
        this.userContext = userContext;
    }

    public User getCurrentUser() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String token = attributes.getRequest().getHeader("Authorization").substring(7);
        return userContext.getCurrentUser(token);
    }
}