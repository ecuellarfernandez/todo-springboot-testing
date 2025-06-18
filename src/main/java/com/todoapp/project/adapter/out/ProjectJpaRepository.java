package com.todoapp.project.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectJpaRepository extends JpaRepository<ProjectEntity, UUID> {
    List<ProjectEntity> findByOwnerId(UUID ownerId);
}