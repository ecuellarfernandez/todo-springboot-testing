package com.todoapp.task.port.out;

import com.todoapp.task.domain.Task;
import java.util.List;
import java.util.UUID;

public interface TaskRepository {
    Task save(Task task);
    Task findById(UUID id);
    List<Task> findByTodoListId(UUID todoListId);
    void delete(UUID id);
    boolean existsById(UUID id);
}