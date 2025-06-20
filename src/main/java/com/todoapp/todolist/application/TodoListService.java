package com.todoapp.todolist.application;

import com.todoapp.common.OwnershipValidator;
import com.todoapp.common.UserProvider;
import com.todoapp.project.domain.Project;
import com.todoapp.project.port.out.ProjectRepository;
import com.todoapp.todolist.domain.TodoList;
import com.todoapp.todolist.domain.mapper.TodoListMapper;
import com.todoapp.todolist.dto.TodoListRequestDTO;
import com.todoapp.todolist.dto.TodoListResponseDTO;
import com.todoapp.todolist.port.in.TodoListUseCase;
import com.todoapp.todolist.port.out.TodoListRepository;
import com.todoapp.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TodoListService implements TodoListUseCase {

    private final TodoListRepository repo;
    private final TodoListMapper mapper;
    private final ProjectRepository projectRepository;
    private final OwnershipValidator ownershipValidator;

    public TodoListService(TodoListRepository repo, TodoListMapper mapper, ProjectRepository projectRepository, OwnershipValidator ownershipValidator) {
        this.repo = repo;
        this.mapper = mapper;
        this.projectRepository = projectRepository;
        this.ownershipValidator = ownershipValidator;
    }

    @Override
    @Transactional
    public TodoListResponseDTO create(TodoListRequestDTO dto) {
        if(!projectRepository.existsById(dto.projectId())) {
            throw new IllegalArgumentException("Project does not exist");
        }

        TodoList todolist = new TodoList(
                null,
                dto.name(),
                dto.projectId()
        );

        TodoList saved = repo.save(todolist);

        return mapper.toTodoListResponseDTO(saved);
    }

    @Override
    public TodoListResponseDTO getById(UUID id) {
        TodoList todoList = repo.findById(id);
        validateOwnership(todoList);
        return mapper.toTodoListResponseDTO(todoList);
    }

    @Override
    public List<TodoListResponseDTO> getByUser() {
        return List.of();
    }

    @Override
    public TodoListResponseDTO update(UUID id, TodoListRequestDTO dto) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }

    private void validateOwnership(TodoList todoList) {
        ownershipValidator.validateTodoListOwnership(todoList);
    }
}
