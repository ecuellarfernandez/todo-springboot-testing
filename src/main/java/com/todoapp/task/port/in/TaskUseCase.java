package com.todoapp.task.port.in;
import com.todoapp.task.dto.TaskRequestDTO;
import com.todoapp.task.dto.TaskResponseDTO;
import com.todoapp.task.dto.TaskStatusUpdateDTO;
import com.todoapp.task.dto.TaskUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface TaskUseCase {
    TaskResponseDTO create(TaskRequestDTO dto);
    TaskResponseDTO getById(UUID id);
    List<TaskResponseDTO> getByTodoList(UUID todoListId);
    TaskResponseDTO update(UUID id, TaskUpdateDTO dto);
    TaskResponseDTO updateStatus(UUID id, TaskStatusUpdateDTO dto);
    void delete(UUID id);
}