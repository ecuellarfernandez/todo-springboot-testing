package com.todoapp.task.application.mapper;

import com.todoapp.project.adapter.out.ProjectEntity;
import com.todoapp.task.adapter.out.TaskEntity;
import com.todoapp.task.domain.Task;
import com.todoapp.task.dto.TaskResponseDTO;
import com.todoapp.todolist.adapter.out.TodoListEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TaskMapperTest {

    private final TaskMapper mapper = Mappers.getMapper(TaskMapper.class);

    private final UUID taskId = UUID.randomUUID();
    private final UUID todoListId = UUID.randomUUID();
    private final UUID projectId = UUID.randomUUID();
    private final LocalDate dueDate = LocalDate.now().plusDays(7);

    @Test
    void shouldMapTaskToResponseDTO() {
        // Given
        Task task = new Task(taskId, "Test Task", "Test Description", false, dueDate, todoListId);

        // When
        TaskResponseDTO result = mapper.toResponseDTO(task);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(taskId);
        assertThat(result.title()).isEqualTo("Test Task");
        assertThat(result.description()).isEqualTo("Test Description");
        assertThat(result.completed()).isEqualTo("false");
        assertThat(result.dueDate()).isEqualTo(dueDate);
        assertThat(result.todoListId()).isEqualTo(todoListId);
    }

    @Test
    void shouldMapTaskToEntity() {
        // Given
        Task task = new Task(taskId, "Test Task", "Test Description", false, dueDate, todoListId);

        // When
        TaskEntity result = mapper.domainToEntity(task);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(taskId);
        assertThat(result.getTitle()).isEqualTo("Test Task");
        assertThat(result.getDescription()).isEqualTo("Test Description");
        assertThat(result.isCompleted()).isFalse();
        assertThat(result.getDueDate()).isEqualTo(dueDate);
    }

    @Test
    void shouldMapEntityToTask() {
        // Given
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(projectId);
        
        TodoListEntity todoListEntity = new TodoListEntity();
        todoListEntity.setId(todoListId);
        todoListEntity.setProject(projectEntity);
        
        TaskEntity entity = new TaskEntity();
        entity.setId(taskId);
        entity.setTitle("Test Task");
        entity.setDescription("Test Description");
        entity.setCompleted(false);
        entity.setDueDate(dueDate);
        entity.setTodoList(todoListEntity);

        // When
        Task result = mapper.entityToDomain(entity);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(taskId);
        assertThat(result.getTitle()).isEqualTo("Test Task");
        assertThat(result.getDescription()).isEqualTo("Test Description");
        assertThat(result.isCompleted()).isFalse();
        assertThat(result.getDueDate()).isEqualTo(dueDate);
        assertThat(result.getTodoListId()).isEqualTo(todoListId);
        assertThat(result.getProjectId()).isEqualTo(projectId);
    }

    @Test
    void shouldMapEntitiesToDomains() {
        // Given
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(projectId);
        
        TodoListEntity todoListEntity = new TodoListEntity();
        todoListEntity.setId(todoListId);
        todoListEntity.setProject(projectEntity);
        
        TaskEntity entity1 = new TaskEntity();
        entity1.setId(taskId);
        entity1.setTitle("Task 1");
        entity1.setTodoList(todoListEntity);
        
        TaskEntity entity2 = new TaskEntity();
        entity2.setId(UUID.randomUUID());
        entity2.setTitle("Task 2");
        entity2.setTodoList(todoListEntity);
        
        List<TaskEntity> entities = Arrays.asList(entity1, entity2);

        // When
        List<Task> result = mapper.entitiesToDomains(entities);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTitle()).isEqualTo("Task 1");
        assertThat(result.get(1).getTitle()).isEqualTo("Task 2");
    }

    @Test
    void shouldHandleNullValuesInTaskToResponseDTO() {
        // Given
        Task task = new Task(taskId, "Test Task", null, false, null, todoListId);

        // When
        TaskResponseDTO result = mapper.toResponseDTO(task);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(taskId);
        assertThat(result.title()).isEqualTo("Test Task");
        assertThat(result.description()).isNull();
        assertThat(result.completed()).isEqualTo("false");
        assertThat(result.dueDate()).isNull();
        assertThat(result.todoListId()).isEqualTo(todoListId);
    }

    @Test
    void shouldHandleNullValuesInEntityToTask() {
        // Given
        TaskEntity entity = new TaskEntity();
        entity.setId(taskId);
        entity.setTitle("Test Task");
        entity.setDescription(null);
        entity.setCompleted(false);
        entity.setDueDate(null);
        entity.setTodoList(null);

        // When
        Task result = mapper.entityToDomain(entity);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(taskId);
        assertThat(result.getTitle()).isEqualTo("Test Task");
        assertThat(result.getDescription()).isNull();
        assertThat(result.isCompleted()).isFalse();
        assertThat(result.getDueDate()).isNull();
        assertThat(result.getTodoListId()).isNull();
        assertThat(result.getProjectId()).isNull();
    }

    @Test
    void shouldHandleEmptyListInEntitiesToDomains() {
        // Given
        List<TaskEntity> entities = Arrays.asList();

        // When
        List<Task> result = mapper.entitiesToDomains(entities);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void shouldMapCompletedTaskCorrectly() {
        // Given
        Task task = new Task(taskId, "Test Task", "Test Description", true, dueDate, todoListId);

        // When
        TaskResponseDTO result = mapper.toResponseDTO(task);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.completed()).isEqualTo("true");
    }
} 