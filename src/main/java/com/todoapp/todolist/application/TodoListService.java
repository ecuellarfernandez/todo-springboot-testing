package com.todoapp.todolist.application;

import com.todoapp.common.OwnershipValidator;
import com.todoapp.common.UserProvider;
import com.todoapp.project.port.out.ProjectRepository;
import com.todoapp.todolist.domain.TodoList;
import com.todoapp.todolist.domain.mapper.TodoListMapper;
import com.todoapp.todolist.dto.TodoListCreateDTO;
import com.todoapp.todolist.dto.TodoListRequestDTO;
import com.todoapp.todolist.dto.TodoListResponseDTO;
import com.todoapp.todolist.dto.TodoListUpdateDTO;
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
    private final UserProvider userProvider;

    public TodoListService(TodoListRepository repo, TodoListMapper mapper, ProjectRepository projectRepository, OwnershipValidator ownershipValidator, UserProvider userProvider) {
        this.repo = repo;
        this.mapper = mapper;
        this.projectRepository = projectRepository;
        this.ownershipValidator = ownershipValidator;
        this.userProvider = userProvider;
    }

    @Override
    @Transactional
    public TodoListResponseDTO create(TodoListCreateDTO dto) {
        if(!projectRepository.existsById(dto.projectId())) {
            throw new IllegalArgumentException("Project does not exist");
        }

        TodoList todolist = new TodoList(
                null,
                dto.name(),
                dto.projectId()
        );

        validateProjectOwnership(dto.projectId());

        TodoList saved = repo.save(todolist);

        return mapper.toTodoListResponseDTO(saved);
    }

    @Override
    public TodoListResponseDTO getById(UUID id) {
        TodoList todoList = repo.findById(id);
        validateOwnership(todoList.getId(), todoList.getProjectId());
        return mapper.toTodoListResponseDTO(todoList);
    }

    @Override
    public List<TodoListResponseDTO> getByUser() {
        User currentUser = userProvider.getCurrentUser();
        List<TodoList> todoLists = repo.findByUserId(currentUser.getId());
        todoLists.forEach(tl -> validateOwnership(tl.getId(), tl.getProjectId()));
        return todoLists.stream()
                .map(mapper::toTodoListResponseDTO)
                .toList();
    }

    @Override
    @Transactional
    public TodoListResponseDTO update(UUID id, TodoListUpdateDTO dto) {
        TodoList existing = repo.findById(id);
        if (existing == null) {
            throw new IllegalArgumentException("No existe la lista");
        }
        validateOwnership(existing.getId(), existing.getProjectId());
        existing.setName(dto.name());
        TodoList updated = repo.save(existing);
        return mapper.toTodoListResponseDTO(updated);
    }

    @Override
    @Transactional
    public void delete(UUID id, UUID projectId) {
        TodoList todoList = repo.findByIdAndProjectId(id, projectId);
        if (todoList == null) {
            throw new IllegalArgumentException("No existe la lista en ese proyecto");
        }
        validateOwnership(todoList.getId(), projectId);
        repo.delete(id, projectId);
    }

    @Override
    public List<TodoListResponseDTO> getByProject(UUID projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new IllegalArgumentException("El proyecto no existe");
        }
        List<TodoList> todoLists = repo.findByProjectId(projectId);
        todoLists.forEach(tl -> validateOwnership(tl.getId(), tl.getProjectId()));
        return todoLists.stream()
                .map(mapper::toTodoListResponseDTO)
                .toList();
    }

    @Override
    public TodoListResponseDTO getByIdAndProject(UUID id, UUID projectId) {
        TodoList todoList = repo.findByIdAndProjectId(id, projectId);
        if (todoList == null) {
            throw new IllegalArgumentException("No existe la lista en ese proyecto");
        }
        validateOwnership(todoList.getId(), projectId);
        return mapper.toTodoListResponseDTO(todoList);
    }

    private void validateOwnership(UUID todoListId, UUID projectId) {
        ownershipValidator.validateTodoListOwnership(todoListId, projectId);
    }

    private void validateProjectOwnership(UUID projectId){
        ownershipValidator.validateProjectOwnership(projectId);
    }
}
