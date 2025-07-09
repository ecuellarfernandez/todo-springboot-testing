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

import java.util.ArrayList;
import java.util.Comparator;
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
        UUID todoListId = dto.todoListId();
        UUID projectId = dto.projectId();

        Task task = new Task(
                null,
                dto.title(),
                dto.description(),
                false,
                dto.dueDate(),
                todoListId
        );

        ownershipValidator.validateTodoListOwnership(todoListId, projectId);

        Task saved = repo.save(task);
        return mapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponseDTO getById(UUID id, UUID todoListId, UUID projectId) {
        if(!repo.existsById(id)){
            throw new IllegalArgumentException("Task with ID " + id + " does not exist.");
        }
        ownershipValidator.validateTodoListOwnership(todoListId, projectId);
        Task task = repo.findById(id);
        if (!task.getTodoListId().equals(todoListId)) {
            throw new IllegalArgumentException("La tarea no pertenece a la lista de tareas especificada.");
        }
        return mapper.toResponseDTO(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getByTodoListId(UUID todoListId, UUID projectId) {
        ownershipValidator.validateTodoListOwnership(todoListId, projectId);

        List<Task> tasks = repo.findByTodoListId(todoListId);
        return tasks.stream()
                .filter(task -> task.getTodoListId().equals(todoListId))
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TaskResponseDTO update(UUID id, TaskUpdateDTO dto, UUID todoListId, UUID projectId) {
        if(!repo.existsById(id)){
            throw new IllegalArgumentException("Task with ID " + id + " does not exist.");
        }

        Task task = repo.findById(id);

        if(!task.getTodoListId().equals(todoListId)) {
            throw new IllegalArgumentException("La tarea no pertenece a la lista de tareas especificada.");
        }

        ownershipValidator.validateTodoListOwnership(todoListId, projectId);

        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setDueDate(dto.dueDate());
        Task updatedTask = repo.save(task);
        return mapper.toResponseDTO(updatedTask);
    }

    @Override
    public TaskResponseDTO updateStatus(UUID id, TaskStatusUpdateDTO dto, UUID todoListId, UUID projectId) {
        if(!repo.existsById(id)){
            throw new IllegalArgumentException("Task with ID " + id + " does not exist.");
        }

        Task task = repo.findById(id);

        if (!task.getTodoListId().equals(todoListId)) {
            throw new IllegalArgumentException("La tarea no pertenece a la lista de tareas especificada.");
        }

        ownershipValidator.validateTodoListOwnership(todoListId, projectId);

        task.setCompleted(dto.completed());
        Task updatedTask = repo.save(task);
        return mapper.toResponseDTO(updatedTask);
    }

    @Override
    @Transactional
    public void delete(UUID id, UUID todoListId, UUID projectId) {
        if(!repo.existsById(id)){
            throw new IllegalArgumentException("Task with ID " + id + " does not exist.");
        }

        Task task = repo.findById(id);
        if (!task.getTodoListId().equals(todoListId)) {
            throw new IllegalArgumentException("La tarea no pertenece a la lista de tareas especificada.");
        }

        ownershipValidator.validateTodoListOwnership(todoListId, projectId);

        repo.delete(id);
    }

    @Override
    @Transactional
    public List<TaskResponseDTO> reorderTasks(UUID projectId, UUID todoListId, List<String> taskIds) {
        ownershipValidator.validateTodoListOwnership(todoListId, projectId);
        List<Task> tasks = repo.findByTodoListId(todoListId);
        // Validar que todos los IDs enviados existen en la lista
        if (!taskIds.stream().allMatch(id -> tasks.stream().anyMatch(t -> t.getId().toString().equals(id)))) {
            throw new org.springframework.web.server.ResponseStatusException(
                org.springframework.http.HttpStatus.BAD_REQUEST,
                "Algunas tareas no pertenecen a la lista o faltan tareas"
            );
        }
        // Actualizar posición y guardar en lote
        for (int i = 0; i < taskIds.size(); i++) {
            UUID taskId = UUID.fromString(taskIds.get(i));
            Task task = tasks.stream().filter(t -> t.getId().equals(taskId)).findFirst()
                    .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.BAD_REQUEST,
                        "Tarea no encontrada: " + taskId
                    ));
            task.setPosition(i);
            repo.save(task);
        }
        // Recuperar y devolver ordenadas solo las tareas reordenadas
        List<Task> updated = new ArrayList<>();
        for (String id : taskIds) {
            Task task = tasks.stream().filter(t -> t.getId().toString().equals(id)).findFirst().get();
            updated.add(task);
        }
        updated.sort(Comparator.comparingInt(Task::getPosition));
        return updated.stream().map(mapper::toResponseDTO).collect(Collectors.toList());
    }
}
