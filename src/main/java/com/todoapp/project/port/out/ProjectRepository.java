package com.todoapp.project.port.out;

import com.todoapp.project.domain.Project;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository {
    Project save(Project project);
    Project findById(UUID id);
    List<Project> findByUserId(UUID userId);
    void delete(UUID id);
    boolean existsById(UUID id);
}