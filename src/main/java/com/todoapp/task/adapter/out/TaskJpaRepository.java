package com.todoapp.task.adapter.out;

import com.todoapp.task.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskJpaRepository extends JpaRepository<TaskEntity, UUID> {
    List<TaskEntity> findByTodoListId(UUID todoListId);
    List<TaskEntity> findByTodoListIdOrderByPositionAsc(UUID todoListId);
}
