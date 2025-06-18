package com.todoapp.task.adapter.out;

import com.todoapp.task.domain.Task;
import com.todoapp.task.port.out.TaskRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    private final Map<UUID, Task> tasks = new ConcurrentHashMap<>();

    @Override
    public Task save(Task task) {
        if (task.getId() == null) {
            task.setId(UUID.randomUUID());
        }
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Task findById(UUID id) {
        return tasks.get(id);
    }

    @Override
    public List<Task> findByTodoListId(UUID todoListId) {
        return tasks.values().stream()
                .filter(task -> task.getTodoListId().equals(todoListId))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        tasks.remove(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return tasks.containsKey(id);
    }
}