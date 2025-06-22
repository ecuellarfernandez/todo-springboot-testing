package com.todoapp.todolist.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TodoListJpaRepository extends JpaRepository<TodoListEntity, UUID> {
    List<TodoListEntity> findByProjectId(UUID projectId);
    Optional<TodoListEntity> findByIdAndProjectId(UUID projectId, UUID id);
    List<TodoListEntity> findByProject_Owner_Id(UUID userId);
}
