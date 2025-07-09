package com.todoapp.task.adapter.out;

import com.todoapp.project.adapter.out.ProjectEntity;
import com.todoapp.task.application.mapper.TaskMapper;
import com.todoapp.task.domain.Task;
import com.todoapp.task.port.out.TaskRepository;
import com.todoapp.todolist.adapter.out.TodoListEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Repository
public class TaskRepositoryImpl implements TaskRepository {
    private final TaskJpaRepository jpa;
    private final TaskMapper mapper;
    @PersistenceContext
    private final EntityManager entityManager;

    public TaskRepositoryImpl(TaskJpaRepository jpa, TaskMapper mapper, EntityManager entityManager) {
        this.jpa = jpa;
        this.mapper = mapper;
        this.entityManager = entityManager;
    }

    @Override
    public Task save(Task task) {
        TaskEntity entity = mapper.domainToEntity(task);
        entity.setTodoList(entityManager.getReference(TodoListEntity.class, task.getTodoListId()));
        TaskEntity savedEntity = jpa.save(entity);
        return mapper.entityToDomain(savedEntity);
    }

    @Override
    public Task findById(UUID id) {
        return jpa.findById(id)
                .map(mapper::entityToDomain)
                .orElseThrow(() -> new NoSuchElementException("Tarea no encontrada con id: " + id));
    }

    @Override
    public List<Task> findByTodoListId(UUID todoListId) {
        return jpa.findByTodoListIdOrderByPositionAsc(todoListId)
                .stream()
                .map(mapper::entityToDomain)
                .toList();
    }

    @Override
    public void delete(UUID id) {
        if(!jpa.existsById(id)) {
            throw new NoSuchElementException("No se encontró la tarea con id: " + id);
        }
        jpa.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpa.existsById(id);
    }
}