package com.todoapp.task.adapter.out;

import com.todoapp.task.application.mapper.TaskMapper;
import com.todoapp.task.domain.Task;
import com.todoapp.todolist.adapter.out.TodoListEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskRepositoryImplTest {

    @Mock
    private TaskJpaRepository jpaRepository;

    @Mock
    private TaskMapper mapper;

    @Mock
    private EntityManager entityManager;

    private TaskRepositoryImpl repository;

    private final UUID taskId = UUID.randomUUID();
    private final UUID todoListId = UUID.randomUUID();
    private final LocalDate dueDate = LocalDate.now().plusDays(7);

    @BeforeEach
    void setUp() {
        repository = new TaskRepositoryImpl(jpaRepository, mapper, entityManager);
    }

    @Test
    void shouldSaveTaskSuccessfully() {
        // Given
        Task task = new Task(taskId, "Test Task", "Test Description", false, dueDate, todoListId);
        TaskEntity entity = new TaskEntity();
        TaskEntity savedEntity = new TaskEntity();
        TodoListEntity todoListEntity = new TodoListEntity();

        when(mapper.domainToEntity(task)).thenReturn(entity);
        when(entityManager.getReference(TodoListEntity.class, todoListId)).thenReturn(todoListEntity);
        when(jpaRepository.save(entity)).thenReturn(savedEntity);
        when(mapper.entityToDomain(savedEntity)).thenReturn(task);

        // When
        Task result = repository.save(task);

        // Then
        assertThat(result).isEqualTo(task);
        verify(mapper).domainToEntity(task);
        verify(entityManager).getReference(TodoListEntity.class, todoListId);
        verify(jpaRepository).save(entity);
        verify(mapper).entityToDomain(savedEntity);
    }

    @Test
    void shouldFindTaskByIdSuccessfully() {
        // Given
        Task task = new Task(taskId, "Test Task", "Test Description", false, dueDate, todoListId);
        TaskEntity entity = new TaskEntity();

        when(jpaRepository.findById(taskId)).thenReturn(Optional.of(entity));
        when(mapper.entityToDomain(entity)).thenReturn(task);

        // When
        Task result = repository.findById(taskId);

        // Then
        assertThat(result).isEqualTo(task);
        verify(jpaRepository).findById(taskId);
        verify(mapper).entityToDomain(entity);
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        // Given
        when(jpaRepository.findById(taskId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> repository.findById(taskId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Tarea no encontrada con id: " + taskId);
    }

    @Test
    void shouldFindTasksByTodoListIdSuccessfully() {
        // Given
        Task task1 = new Task(taskId, "Task 1", "Description 1", false, dueDate, todoListId);
        Task task2 = new Task(UUID.randomUUID(), "Task 2", "Description 2", true, dueDate, todoListId);
        TaskEntity entity1 = new TaskEntity();
        TaskEntity entity2 = new TaskEntity();

        List<TaskEntity> entities = Arrays.asList(entity1, entity2);
        List<Task> tasks = Arrays.asList(task1, task2);

        when(jpaRepository.findByTodoListId(todoListId)).thenReturn(entities);
        when(mapper.entitiesToDomains(entities)).thenReturn(tasks);

        // When
        List<Task> result = repository.findByTodoListId(todoListId);

        // Then
        assertThat(result).isEqualTo(tasks);
        verify(jpaRepository).findByTodoListId(todoListId);
        verify(mapper).entitiesToDomains(entities);
    }

    @Test
    void shouldThrowExceptionWhenNoTasksFoundForTodoList() {
        // Given
        when(jpaRepository.findByTodoListId(todoListId)).thenReturn(Arrays.asList());

        // When & Then
        assertThatThrownBy(() -> repository.findByTodoListId(todoListId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("No se encontraron tareas para la lista de tareas con id: " + todoListId);
    }

    @Test
    void shouldDeleteTaskSuccessfully() {
        // Given
        when(jpaRepository.existsById(taskId)).thenReturn(true);
        doNothing().when(jpaRepository).deleteById(taskId);

        // When
        repository.delete(taskId);

        // Then
        verify(jpaRepository).existsById(taskId);
        verify(jpaRepository).deleteById(taskId);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentTask() {
        // Given
        when(jpaRepository.existsById(taskId)).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> repository.delete(taskId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("No se encontró la tarea con id: " + taskId);
        
        verify(jpaRepository).existsById(taskId);
        verify(jpaRepository, never()).deleteById(any());
    }

    @Test
    void shouldCheckIfTaskExists() {
        // Given
        when(jpaRepository.existsById(taskId)).thenReturn(true);

        // When
        boolean result = repository.existsById(taskId);

        // Then
        assertThat(result).isTrue();
        verify(jpaRepository).existsById(taskId);
    }

    @Test
    void shouldReturnFalseWhenTaskDoesNotExist() {
        // Given
        when(jpaRepository.existsById(taskId)).thenReturn(false);

        // When
        boolean result = repository.existsById(taskId);

        // Then
        assertThat(result).isFalse();
        verify(jpaRepository).existsById(taskId);
    }
} 