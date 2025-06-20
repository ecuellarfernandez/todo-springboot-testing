package com.todoapp.common;

import com.todoapp.project.domain.Project;
import com.todoapp.project.port.out.ProjectRepository;
import com.todoapp.todolist.domain.TodoList;
import com.todoapp.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class OwnershipValidator {
    private final UserProvider userProvider;
    private final ProjectRepository projectRepository;

    public OwnershipValidator(UserProvider userProvider, ProjectRepository projectRepository) {
        this.userProvider = userProvider;
        this.projectRepository = projectRepository;
    }

    public void validateTodoListOwnership(TodoList todoList) {
        User currentUser = userProvider.getCurrentUser();
        Project project = projectRepository.findById(todoList.getProjectId());
        if (!project.getUserId().equals(currentUser.getId())) {
            throw new SecurityException("No tienes permiso para acceder a este recurso");
        }
    }

    public void validateProjectOwnership(Project project) {
        User currentUser = userProvider.getCurrentUser();
        if (!project.getUserId().equals(currentUser.getId())) {
            throw new SecurityException("No tienes permiso para acceder a este recurso");
        }
    }
}