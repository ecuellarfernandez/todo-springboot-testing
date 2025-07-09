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
@RequestMapping("/api/projects/{projectId}/todolists/{todoListId}/tasks")
public class TaskController {
    private final TaskUseCase useCase;

    public TaskController(TaskUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> create(
            @PathVariable UUID projectId,
            @PathVariable UUID todoListId,
            @Valid @RequestBody TaskRequestDTO dto) {

        TaskCreateDTO createDTO = new TaskCreateDTO(
                dto.title(),
                dto.description(),
                dto.dueDate(),
                todoListId,
                projectId
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(useCase.create(createDTO));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getByTodoList(
            @PathVariable UUID projectId,
            @PathVariable UUID todoListId) {
        return ResponseEntity.ok(useCase.getByTodoListId(todoListId, projectId));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> findById(
            @PathVariable UUID projectId,
            @PathVariable UUID todoListId,
            @PathVariable UUID taskId) {
        return ResponseEntity.ok(useCase.getById(taskId, todoListId, projectId));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> update(
            @PathVariable UUID projectId,
            @PathVariable UUID todoListId,
            @PathVariable UUID taskId,
            @Valid @RequestBody TaskRequestDTO dto) {
        TaskUpdateDTO updateDTO = new TaskUpdateDTO(
                dto.title(),
                dto.description(),
                dto.dueDate()
        );
        return ResponseEntity.ok(useCase.update(taskId, updateDTO, todoListId, projectId));
    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<TaskResponseDTO> updateStatus(
            @PathVariable UUID projectId,
            @PathVariable UUID todoListId,
            @PathVariable UUID taskId,
            @Valid @RequestBody TaskStatusUpdateDTO dto) {
        return ResponseEntity.ok(useCase.updateStatus(taskId, dto, todoListId, projectId));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID projectId,
            @PathVariable UUID todoListId,
            @PathVariable UUID taskId) {
        useCase.delete(taskId, todoListId, projectId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/reorder")
    public ResponseEntity<?> reorderTasks(
            @PathVariable UUID projectId,
            @PathVariable UUID todoListId,
            @RequestBody(required = false) TaskReorderDTO reorderDTO) {
        if (reorderDTO == null || reorderDTO.getTaskIds() == null) {
            return ResponseEntity.badRequest().body("El cuerpo de la petición debe incluir el campo 'taskIds' como un array.");
        }
        try {
            List<TaskResponseDTO> updatedTasks = useCase.reorderTasks(projectId, todoListId, reorderDTO.getTaskIds());
            return ResponseEntity.ok(updatedTasks);
        } catch (org.springframework.web.server.ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error interno del servidor: " + (e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName()));
        }
    }
}