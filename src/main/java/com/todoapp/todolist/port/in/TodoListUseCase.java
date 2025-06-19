package com.todoapp.todolist.port.in;

import com.todoapp.todolist.dto.TodoListRequestDTO;
import com.todoapp.todolist.dto.TodoListResponseDTO;

import java.util.List;
import java.util.UUID;

public interface TodoListUseCase {
    TodoListResponseDTO create(TodoListRequestDTO dto);
    TodoListResponseDTO findById(UUID id);
    List<TodoListResponseDTO> getByUser();
    TodoListResponseDTO update(UUID id, TodoListRequestDTO dto);
    void delete(UUID id);
}
