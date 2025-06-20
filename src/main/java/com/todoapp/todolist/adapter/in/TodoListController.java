package com.todoapp.todolist.adapter.in;

import com.todoapp.todolist.dto.TodoListRequestDTO;
import com.todoapp.todolist.dto.TodoListResponseDTO;
import com.todoapp.todolist.port.in.TodoListUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/todolists")
public class TodoListController {
    private final TodoListUseCase useCase;

    public TodoListController(TodoListUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public ResponseEntity<TodoListResponseDTO> create(@Valid @RequestBody TodoListRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(useCase.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoListResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(useCase.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<TodoListResponseDTO>> getByUser() {
        return ResponseEntity.ok(useCase.getByUser());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoListResponseDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody TodoListRequestDTO dto) {
        return ResponseEntity.ok(useCase.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
