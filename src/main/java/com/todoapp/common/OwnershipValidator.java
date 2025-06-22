package com.todoapp.common;

import com.todoapp.project.domain.Project;
import com.todoapp.project.port.out.ProjectRepository;
import com.todoapp.todolist.domain.TodoList;
import com.todoapp.todolist.port.out.TodoListRepository;
import com.todoapp.user.domain.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OwnershipValidator {
    private final UserProvider userProvider;
    private final ProjectRepository projectRepository;
    private final TodoListRepository todoListRepository;

    public OwnershipValidator(UserProvider userProvider, ProjectRepository projectRepository, TodoListRepository todoListRepository) {
        this.userProvider = userProvider;
        this.projectRepository = projectRepository;
        this.todoListRepository = todoListRepository;
    }

    public void validateTodoListOwnership(UUID todoListId, UUID projectId) {
        User currentUser = userProvider.getCurrentUser();
        Project project = projectRepository.findById(projectId);
        if (project == null) {
            throw new IllegalArgumentException("El proyecto especificado no existe");
        }
        TodoList todoList = todoListRepository.findById(todoListId);
        if (todoList == null) {
            throw new IllegalArgumentException("El TodoList especificado no existe");
        }
        if(!todoList.getProjectId().equals(projectId)) {
            throw new IllegalArgumentException("El TodoList no pertenece al proyecto especificado");
        }
        if (!project.getUserId().equals(currentUser.getId())) {
            throw new SecurityException("No tienes permiso para acceder a este recurso");
        }
    }

    public void validateProjectOwnership(UUID projectId) {
        User currentUser = userProvider.getCurrentUser();
        Project project = projectRepository.findById(projectId);
        if (!project.getUserId().equals(currentUser.getId())) {
            throw new SecurityException("No tienes permiso para acceder a este recurso");
        }
    }
}