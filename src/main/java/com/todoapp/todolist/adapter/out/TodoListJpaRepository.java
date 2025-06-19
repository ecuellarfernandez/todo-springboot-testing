package com.todoapp.todolist.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TodoListJpaRepository extends JpaRepository<TodoListEntity, UUID> {
    List<TodoListEntity> findByProjectId(UUID projectId);
}
