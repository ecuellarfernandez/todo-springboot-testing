package com.todoapp.task.application;

import com.todoapp.common.OwnershipValidator;
import com.todoapp.project.domain.Project;
import com.todoapp.project.port.out.ProjectRepository;
import com.todoapp.todolist.domain.TodoList;
import com.todoapp.todolist.port.out.TodoListRepository;
import com.todoapp.task.application.mapper.TaskMapper;
import com.todoapp.task.domain.Task;
import com.todoapp.task.dto.*;
import com.todoapp.task.port.in.TaskUseCase;
import com.todoapp.task.port.out.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskService implements TaskUseCase {

    private final TaskRepository repo;
    private final TaskMapper mapper;
    private final OwnershipValidator ownershipValidator;
    private final TodoListRepository todoListRepository;
    private final ProjectRepository projectRepository;

    public TaskService(TaskRepository repo, TaskMapper mapper, OwnershipValidator ownershipValidator,
                       TodoListRepository todoListRepository, ProjectRepository projectRepository) {
        this.repo = repo;
        this.mapper = mapper;
        this.ownershipValidator = ownershipValidator;
        this.todoListRepository = todoListRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    @Transactional
    public TaskResponseDTO create(TaskCreateDTO dto) {
        TodoList todoList = todoListRepository.findById(dto.todoListId());
        Project project = projectRepository.findById(todoList.getProjectId());
        ownershipValidator.validateProjectOwnership(project);

        Task task = new Task(
                null,
                dto.title(),
                dto.description(),
                false,
                dto.dueDate(),
                dto.todoListId()
        );

        Task saved = repo.save(task);
        return mapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponseDTO getById(UUID id) {
        Task task = repo.findById(id);
        TodoList todoList = todoListRepository.findById(task.getTodoListId());
        Project project = projectRepository.findById(todoList.getProjectId());
        ownershipValidator.validateProjectOwnership(project);

        return mapper.toResponseDTO(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getByTodoListId(UUID todoListId, UUID projectId) {
        TodoList todoList = todoListRepository.findById(todoListId);
        Project project = projectRepository.findById(todoList.getProjectId());
        ownershipValidator.validateProjectOwnership(project);

        List<Task> tasks = repo.findByTodoListId(todoListId);
        return tasks.stream()
                .filter(task -> task.getTodoListId().equals(todoListId))
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TaskResponseDTO update(UUID id, TaskUpdateDTO dto) {
        if(!repo.existsById(id)){
            throw new IllegalArgumentException("Task with ID " + id + " does not exist.");
        }

        Task task = repo.findById(id);
        TodoList todoList = todoListRepository.findById(task.getTodoListId());
        Project project = projectRepository.findById(todoList.getProjectId());
        ownershipValidator.validateProjectOwnership(project);

        if (dto.title() != null) {
            task.setTitle(dto.title());
        }
        if (dto.description() != null) {
            task.setDescription(dto.description());
        }
        if (dto.dueDate() != null) {
            task.setDueDate(dto.dueDate());
        }

        Task updated = repo.save(task);
        return mapper.toResponseDTO(updated);
    }

    @Override
    public TaskResponseDTO updateStatus(UUID id, TaskStatusUpdateDTO dto) {
        if(!repo.existsById(id)){
            throw new IllegalArgumentException("Task with ID " + id + " does not exist.");
        }

        Task task = repo.findById(id);
        TodoList todoList = todoListRepository.findById(task.getTodoListId());
        Project project = projectRepository.findById(todoList.getProjectId());
        ownershipValidator.validateProjectOwnership(project);

        task.setCompleted(dto.completed());
        Task updated = repo.save(task);
        return mapper.toResponseDTO(updated);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        if(!repo.existsById(id)){
            throw new IllegalArgumentException("Task with ID " + id + " does not exist.");
        }

        Task task = repo.findById(id);
        TodoList todoList = todoListRepository.findById(task.getTodoListId());
        Project project = projectRepository.findById(todoList.getProjectId());
        ownershipValidator.validateProjectOwnership(project);

        repo.delete(id);
    }
}