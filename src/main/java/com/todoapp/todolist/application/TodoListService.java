package com.todoapp.todolist.application;

import com.todoapp.todolist.domain.mapper.TodoListMapper;
import com.todoapp.todolist.dto.TodoListRequestDTO;
import com.todoapp.todolist.dto.TodoListResponseDTO;
import com.todoapp.todolist.port.in.TodoListUseCase;
import com.todoapp.todolist.port.out.TodoListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TodoListService implements TodoListUseCase {

    private final TodoListRepository repo;
    private final TodoListMapper mapper;

    public TodoListService(TodoListRepository repo, TodoListMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public TodoListResponseDTO create(TodoListRequestDTO dto) {
        return null;
    }

    @Override
    public TodoListResponseDTO findById(UUID id) {
        return null;
    }

    @Override
    public List<TodoListResponseDTO> getByUser() {
        return List.of();
    }

    @Override
    public TodoListResponseDTO update(UUID id, TodoListRequestDTO dto) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
