package com.todoapp.task.application.mapper;

import com.todoapp.task.domain.Task;
import com.todoapp.task.dto.TaskResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public TaskResponseDTO toResponseDTO(Task task) {
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