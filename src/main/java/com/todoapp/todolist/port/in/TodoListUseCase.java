package com.todoapp.todolist.port.in;

import com.todoapp.todolist.dto.TodoListCreateDTO;
import com.todoapp.todolist.dto.TodoListRequestDTO;
import com.todoapp.todolist.dto.TodoListResponseDTO;
import com.todoapp.todolist.dto.TodoListUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface TodoListUseCase {
    TodoListResponseDTO create(TodoListCreateDTO dto);
    TodoListResponseDTO getById(UUID id);
    List<TodoListResponseDTO> getByUser();
    TodoListResponseDTO update(UUID id, TodoListUpdateDTO dto);
    void delete(UUID id, UUID projectId);
    List<TodoListResponseDTO> getByProject(UUID projectId);
    TodoListResponseDTO getByIdAndProject(UUID id, UUID projectId);
}
