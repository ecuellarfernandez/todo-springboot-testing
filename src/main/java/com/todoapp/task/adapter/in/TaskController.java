package com.todoapp.task.adapter.in;

import com.todoapp.task.dto.*;
import com.todoapp.task.port.in.TaskUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskUseCase useCase;

    public TaskController(TaskUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> create(@Valid @RequestBody TaskRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(useCase.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(useCase.getById(id));
    }

    @GetMapping("/list/{todoListId}")
    public ResponseEntity<List<TaskResponseDTO>> getByTodoList(@PathVariable UUID todoListId) {
        return ResponseEntity.ok(useCase.getByTodoList(todoListId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody TaskUpdateDTO dto) {
        return ResponseEntity.ok(useCase.update(id, dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponseDTO> updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody TaskStatusUpdateDTO dto) {
        return ResponseEntity.ok(useCase.updateStatus(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}