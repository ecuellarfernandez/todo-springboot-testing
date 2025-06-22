package com.todoapp.task.port.in;
import com.todoapp.task.dto.*;

import java.util.List;
import java.util.UUID;

public interface TaskUseCase {
    TaskResponseDTO create(TaskCreateDTO dto);
    TaskResponseDTO getById(UUID id, UUID todoListId, UUID projectId);
    List<TaskResponseDTO> getByTodoListId(UUID todoListId, UUID projectId);
    TaskResponseDTO update(UUID id, TaskUpdateDTO dto, UUID todoListId, UUID projectId);
    TaskResponseDTO updateStatus(UUID id, TaskStatusUpdateDTO dto, UUID todoListId, UUID projectId);
    void delete(UUID id, UUID todoListId, UUID projectId);
}