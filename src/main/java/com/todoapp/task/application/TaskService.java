package com.todoapp.task.application;

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

    public TaskService(TaskRepository repo, TaskMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
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
        return mapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponseDTO getById(UUID id) {
        Task task = repo.findById(id);
        return mapper.toResponseDTO(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getByTodoList(UUID todoListId) {
        return repo.findByTodoListId(todoListId).stream()
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
    @Transactional
    public TaskResponseDTO updateStatus(UUID id, TaskStatusUpdateDTO dto) {
        Task task = repo.findById(id);
        task.setCompleted(dto.completed());
        Task updated = repo.save(task);
        return mapper.toResponseDTO(updated);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        repo.delete(id);
    }
}