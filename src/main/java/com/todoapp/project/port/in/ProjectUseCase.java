package com.todoapp.project.port.in;

import com.todoapp.project.dto.ProjectRequestDTO;
import com.todoapp.project.dto.ProjectResponseDTO;
import com.todoapp.project.dto.ProjectUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface ProjectUseCase {
    ProjectResponseDTO create(ProjectRequestDTO dto);
    ProjectResponseDTO getById(UUID id);
    List<ProjectResponseDTO> getByUser();
    ProjectResponseDTO update(UUID id, ProjectUpdateDTO dto);
    void delete(UUID id);
}