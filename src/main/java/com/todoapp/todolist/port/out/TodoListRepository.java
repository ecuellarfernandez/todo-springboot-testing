package com.todoapp.todolist.port.out;

import com.todoapp.todolist.domain.TodoList;

import java.util.List;
import java.util.UUID;

public interface TodoListRepository {
    TodoList save(TodoList todoList);
    TodoList findById(UUID id);
    List<TodoList> findByProjectId(UUID projectId);
    void delete(UUID id);
    boolean existsByNameAndProjectId(String name, UUID projectId);
}
