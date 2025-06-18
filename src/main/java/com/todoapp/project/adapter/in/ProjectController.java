package com.todoapp.project.adapter.in;

import com.todoapp.project.dto.ProjectRequestDTO;
import com.todoapp.project.dto.ProjectResponseDTO;
import com.todoapp.project.dto.ProjectUpdateDTO;
import com.todoapp.project.port.in.ProjectUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectUseCase useCase;

    public ProjectController(ProjectUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> create(@Valid @RequestBody ProjectRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(useCase.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(useCase.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getByUser() {
        return ResponseEntity.ok(useCase.getByUser());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> update(
            @PathVariable UUID id,
            @Valid @RequestBody ProjectUpdateDTO dto) {
        return ResponseEntity.ok(useCase.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}