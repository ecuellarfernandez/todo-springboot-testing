package com.todoapp.todolist.adapter.out;

import com.todoapp.project.adapter.out.ProjectEntity;
import com.todoapp.todolist.domain.TodoList;
import com.todoapp.todolist.domain.mapper.TodoListMapper;
import com.todoapp.todolist.port.out.TodoListRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class TodoListRepositoryImpl implements TodoListRepository {

    private final TodoListJpaRepository jpa;
    private final TodoListMapper mapper;
    @PersistenceContext
    private EntityManager entityManager;

    public TodoListRepositoryImpl(TodoListJpaRepository jpa, TodoListMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public TodoList save(TodoList todoList) {
        TodoListEntity entity = mapper.domainToEntity(todoList);
        entity.setProject(entityManager.getReference(ProjectEntity.class, todoList.getProjectId()));
        TodoListEntity savedEntity = jpa.save(entity);
        return mapper.entityToDomain(savedEntity);
    }

    @Override
    public TodoList findById(UUID id) {
        return jpa.findById(id)
                .map(mapper::entityToDomain)
                .orElseThrow(() -> new NoSuchElementException("Todo list no encontrado con id: " + id));
    }

    @Override
    public List<TodoList> findByProjectId(UUID projectId) {
        List<TodoListEntity> entities = jpa.findByProjectId(projectId);
        if (entities.isEmpty()) {
            throw new NoSuchElementException("No se encontraron listas de tareas para el proyecto con id: " + projectId);
        }
        return mapper.entitiesToDomains(entities);
    }

    @Override
    public void delete(UUID id) {
        if (!jpa.existsById(id)) {
            throw new NoSuchElementException("No se encontró la lista de tareas con id: " + id);
        }
        jpa.deleteById(id);
    }

    @Override
    public boolean existsByNameAndProjectId(String name, UUID projectId) {
        if (name == null || projectId == null) {
            throw new IllegalArgumentException("El nombre y el ID del proyecto no pueden ser nulos");
        }
        return jpa.existsByNameAndProjectId(name, projectId);
    }
}
