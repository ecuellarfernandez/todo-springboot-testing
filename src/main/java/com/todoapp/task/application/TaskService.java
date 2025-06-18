package com.todoapp.task.application;

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

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional
    public TaskResponseDTO create(TaskRequestDTO dto) {
        Task task = new Task(
                null,
                dto.title(),
                dto.description(),
                false,
                dto.dueDate(),
                dto.todoListId()
        );

        Task saved = repo.save(task);
        return mapToResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponseDTO getById(UUID id) {
        Task task = repo.findById(id);
        return mapToResponseDTO(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getByTodoList(UUID todoListId) {
        return repo.findByTodoListId(todoListId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TaskResponseDTO update(UUID id, TaskUpdateDTO dto) {
        Task task = repo.findById(id);

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
        return mapToResponseDTO(updated);
    }

    @Override
    @Transactional
    public TaskResponseDTO updateStatus(UUID id, TaskStatusUpdateDTO dto) {
        Task task = repo.findById(id);
        task.setCompleted(dto.completed());
        Task updated = repo.save(task);
        return mapToResponseDTO(updated);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        repo.delete(id);
    }

    private TaskResponseDTO mapToResponseDTO(Task task) {
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted(),
                task.getDueDate(),
                task.getTodoListId()
        );
    }
}