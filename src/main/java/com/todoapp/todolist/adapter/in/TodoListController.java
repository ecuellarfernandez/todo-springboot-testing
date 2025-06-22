package com.todoapp.todolist.adapter.in;

import com.todoapp.todolist.dto.TodoListCreateDTO;
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
@RequestMapping("/api/projects/{projectId}/todolists")
public class TodoListController {
    private final TodoListUseCase useCase;

    public TodoListController(TodoListUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public ResponseEntity<TodoListResponseDTO> create(
            @PathVariable UUID projectId,
            @Valid @RequestBody TodoListRequestDTO dto) {
        TodoListCreateDTO todoListCreateDTO = new TodoListCreateDTO(dto.name(), projectId);
        return ResponseEntity.status(HttpStatus.CREATED).body(useCase.create(todoListCreateDTO));
    }

    @GetMapping
    public ResponseEntity<List<TodoListResponseDTO>> getByProject(@PathVariable UUID projectId) {
        return ResponseEntity.ok(useCase.getByProject(projectId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoListResponseDTO> findById(
            @PathVariable UUID projectId,
            @PathVariable UUID id) {
        // Validar que la todolist pertenezca al proyecto
        return ResponseEntity.ok(useCase.getByIdAndProject(id, projectId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoListResponseDTO> update(
            @PathVariable UUID projectId,
            @PathVariable UUID id,
            @Valid @RequestBody TodoListRequestDTO dto) {
        TodoListRequestDTO dtoWithProject = new TodoListRequestDTO(dto.name(), projectId);
        return ResponseEntity.ok(useCase.update(id, dtoWithProject));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID projectId,
            @PathVariable UUID id) {
        useCase.delete(id, projectId);
        return ResponseEntity.noContent().build();
    }
}