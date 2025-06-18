package com.todoapp.task.application;

import com.todoapp.task.application.mapper.TaskMapper;
import com.todoapp.task.domain.Task;
import com.todoapp.task.dto.*;
import com.todoapp.task.port.out.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository repo;

    @Mock
    private TaskMapper mapper;

    @InjectMocks
    private TaskService service;

    private UUID taskId;
    private UUID todoListId;
    private Task task;
    private TaskRequestDTO taskRequestDTO;
    private TaskResponseDTO taskResponseDTO;
    private TaskUpdateDTO taskUpdateDTO;
    private TaskStatusUpdateDTO taskStatusUpdateDTO;

    @BeforeEach
    void setUp() {
        taskId = UUID.randomUUID();
        todoListId = UUID.randomUUID();
        
        task = new Task(
            taskId,
            "Tarea de prueba",
            "Descripción de la tarea",
            false,
            LocalDateTime.now().plusDays(1),
            todoListId
        );
        
        taskRequestDTO = new TaskRequestDTO(
            "Tarea de prueba",
            "Descripción de la tarea",
            LocalDateTime.now().plusDays(1),
            todoListId
        );
        
        taskResponseDTO = new TaskResponseDTO(
            taskId,
            "Tarea de prueba",
            "Descripción de la tarea",
            false,
            LocalDateTime.now().plusDays(1),
            todoListId
        );
        
        taskUpdateDTO = new TaskUpdateDTO(
            "Tarea actualizada",
            "Descripción actualizada",
            LocalDateTime.now().plusDays(2)
        );
        
        taskStatusUpdateDTO = new TaskStatusUpdateDTO(true);
    }

    @Test
    void create_ShouldReturnCreatedTask() {
        // Given
        Task savedTask = new Task(
            taskId,
            "Tarea de prueba",
            "Descripción de la tarea",
            false,
            LocalDateTime.now().plusDays(1),
            todoListId
        );
        
        when(repo.save(any(Task.class))).thenReturn(savedTask);
        when(mapper.toResponseDTO(savedTask)).thenReturn(taskResponseDTO);

        // When
        TaskResponseDTO result = service.create(taskRequestDTO);

        // Then
        assertNotNull(result);
        assertEquals(taskId, result.id());
        assertEquals("Tarea de prueba", result.title());
        assertEquals("Descripción de la tarea", result.description());
        assertFalse(result.completed());
        assertEquals(todoListId, result.todoListId());

        verify(repo).save(any(Task.class));
        verify(mapper).toResponseDTO(savedTask);
    }

    @Test
    void create_WithNullValues_ShouldCreateTaskWithDefaults() {
        // Given
        TaskRequestDTO requestWithNulls = new TaskRequestDTO(
            "Tarea",
            null,
            null,
            todoListId
        );
        
        Task savedTask = new Task(taskId, "Tarea", null, false, null, todoListId);
        TaskResponseDTO responseWithNulls = new TaskResponseDTO(
            taskId, "Tarea", null, false, null, todoListId
        );
        
        when(repo.save(any(Task.class))).thenReturn(savedTask);
        when(mapper.toResponseDTO(savedTask)).thenReturn(responseWithNulls);

        // When
        TaskResponseDTO result = service.create(requestWithNulls);

        // Then
        assertNotNull(result);
        assertEquals("Tarea", result.title());
        assertNull(result.description());
        assertNull(result.dueDate());
        assertFalse(result.completed());

        verify(repo).save(any(Task.class));
    }

    @Test
    void getById_ShouldReturnTask() {
        // Given
        when(repo.findById(taskId)).thenReturn(task);
        when(mapper.toResponseDTO(task)).thenReturn(taskResponseDTO);

        // When
        TaskResponseDTO result = service.getById(taskId);

        // Then
        assertNotNull(result);
        assertEquals(taskId, result.id());
        assertEquals("Tarea de prueba", result.title());
        assertEquals("Descripción de la tarea", result.description());
        assertFalse(result.completed());
        assertEquals(todoListId, result.todoListId());

        verify(repo).findById(taskId);
        verify(mapper).toResponseDTO(task);
    }

    @Test
    void getById_WithNonExistentId_ShouldReturnNull() {
        // Given
        when(repo.findById(taskId)).thenReturn(null);
        when(mapper.toResponseDTO(null)).thenReturn(null);

        // When
        TaskResponseDTO result = service.getById(taskId);

        // Then
        assertNull(result);

        verify(repo).findById(taskId);
        verify(mapper).toResponseDTO(null);
    }

    @Test
    void getByTodoList_ShouldReturnTaskList() {
        // Given
        List<Task> tasks = Arrays.asList(task);
        List<TaskResponseDTO> expectedResponses = Arrays.asList(taskResponseDTO);
        
        when(repo.findByTodoListId(todoListId)).thenReturn(tasks);
        when(mapper.toResponseDTO(task)).thenReturn(taskResponseDTO);

        // When
        List<TaskResponseDTO> result = service.getByTodoList(todoListId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(taskId, result.get(0).id());
        assertEquals("Tarea de prueba", result.get(0).title());

        verify(repo).findByTodoListId(todoListId);
        verify(mapper).toResponseDTO(task);
    }

    @Test
    void getByTodoList_WithEmptyList_ShouldReturnEmptyList() {
        // Given
        when(repo.findByTodoListId(todoListId)).thenReturn(Arrays.asList());

        // When
        List<TaskResponseDTO> result = service.getByTodoList(todoListId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repo).findByTodoListId(todoListId);
        verify(mapper, never()).toResponseDTO(any());
    }

    @Test
    void update_ShouldReturnUpdatedTask() {
        // Given
        Task updatedTask = new Task(
            taskId,
            "Tarea actualizada",
            "Descripción actualizada",
            false,
            LocalDateTime.now().plusDays(2),
            todoListId
        );
        
        TaskResponseDTO updatedResponse = new TaskResponseDTO(
            taskId,
            "Tarea actualizada",
            "Descripción actualizada",
            false,
            LocalDateTime.now().plusDays(2),
            todoListId
        );
        
        when(repo.existsById(taskId)).thenReturn(true);
        when(repo.findById(taskId)).thenReturn(task);
        when(repo.save(task)).thenReturn(updatedTask);
        when(mapper.toResponseDTO(updatedTask)).thenReturn(updatedResponse);

        // When
        TaskResponseDTO result = service.update(taskId, taskUpdateDTO);

        // Then
        assertNotNull(result);
        assertEquals(taskId, result.id());
        assertEquals("Tarea actualizada", result.title());
        assertEquals("Descripción actualizada", result.description());

        verify(repo).existsById(taskId);
        verify(repo).findById(taskId);
        verify(repo).save(task);
        verify(mapper).toResponseDTO(updatedTask);
    }

    @Test
    void update_WithNonExistentId_ShouldThrowException() {
        // Given
        when(repo.existsById(taskId)).thenReturn(false);

        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> service.update(taskId, taskUpdateDTO)
        );

        assertEquals("Task with ID " + taskId + " does not exist.", exception.getMessage());

        verify(repo).existsById(taskId);
        verify(repo, never()).findById(any());
        verify(repo, never()).save(any());
        verify(mapper, never()).toResponseDTO(any());
    }

    @Test
    void update_WithPartialData_ShouldUpdateOnlyProvidedFields() {
        // Given
        TaskUpdateDTO partialUpdate = new TaskUpdateDTO(
            "Nuevo título",
            null,
            null
        );
        
        Task updatedTask = new Task(
            taskId,
            "Nuevo título",
            "Descripción de la tarea", // No cambió
            false,
            LocalDateTime.now().plusDays(1), // No cambió
            todoListId
        );
        
        TaskResponseDTO updatedResponse = new TaskResponseDTO(
            taskId,
            "Nuevo título",
            "Descripción de la tarea",
            false,
            LocalDateTime.now().plusDays(1),
            todoListId
        );
        
        when(repo.existsById(taskId)).thenReturn(true);
        when(repo.findById(taskId)).thenReturn(task);
        when(repo.save(task)).thenReturn(updatedTask);
        when(mapper.toResponseDTO(updatedTask)).thenReturn(updatedResponse);

        // When
        TaskResponseDTO result = service.update(taskId, partialUpdate);

        // Then
        assertNotNull(result);
        assertEquals("Nuevo título", result.title());
        assertEquals("Descripción de la tarea", result.description()); // No cambió

        verify(repo).existsById(taskId);
        verify(repo).findById(taskId);
        verify(repo).save(task);
    }

    @Test
    void updateStatus_ShouldReturnUpdatedTask() {
        // Given
        Task updatedTask = new Task(
            taskId,
            "Tarea de prueba",
            "Descripción de la tarea",
            true, // Completada
            LocalDateTime.now().plusDays(1),
            todoListId
        );
        
        TaskResponseDTO updatedResponse = new TaskResponseDTO(
            taskId,
            "Tarea de prueba",
            "Descripción de la tarea",
            true, // Completada
            LocalDateTime.now().plusDays(1),
            todoListId
        );
        
        when(repo.findById(taskId)).thenReturn(task);
        when(repo.save(task)).thenReturn(updatedTask);
        when(mapper.toResponseDTO(updatedTask)).thenReturn(updatedResponse);

        // When
        TaskResponseDTO result = service.updateStatus(taskId, taskStatusUpdateDTO);

        // Then
        assertNotNull(result);
        assertEquals(taskId, result.id());
        assertEquals("Tarea de prueba", result.title());
        assertTrue(result.completed()); // Ahora está completada

        verify(repo).findById(taskId);
        verify(repo).save(task);
        verify(mapper).toResponseDTO(updatedTask);
    }

    @Test
    void updateStatus_WithNonExistentId_ShouldThrowNullPointerException() {
        // Given
        when(repo.findById(taskId)).thenReturn(null);

        // When & Then
        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> service.updateStatus(taskId, taskStatusUpdateDTO)
        );

        verify(repo).findById(taskId);
        verify(repo, never()).save(any());
        verify(mapper, never()).toResponseDTO(any());
    }

    @Test
    void delete_ShouldCallRepository() {
        // Given
        doNothing().when(repo).delete(taskId);

        // When
        service.delete(taskId);

        // Then
        verify(repo).delete(taskId);
    }

    @Test
    void delete_WithNonExistentId_ShouldStillCallRepository() {
        // Given
        doNothing().when(repo).delete(taskId);

        // When
        service.delete(taskId);

        // Then
        verify(repo).delete(taskId);
    }

    @Test
    void create_ShouldSetCompletedToFalse() {
        // Given
        Task savedTask = new Task(
            taskId,
            "Tarea de prueba",
            "Descripción de la tarea",
            false, // Siempre false al crear
            LocalDateTime.now().plusDays(1),
            todoListId
        );
        
        TaskResponseDTO response = new TaskResponseDTO(
            taskId,
            "Tarea de prueba",
            "Descripción de la tarea",
            false, // Siempre false al crear
            LocalDateTime.now().plusDays(1),
            todoListId
        );
        
        when(repo.save(any(Task.class))).thenReturn(savedTask);
        when(mapper.toResponseDTO(savedTask)).thenReturn(response);

        // When
        TaskResponseDTO result = service.create(taskRequestDTO);

        // Then
        assertNotNull(result);
        assertFalse(result.completed()); // Debe ser false al crear

        verify(repo).save(argThat(task -> !task.isCompleted())); // Verificar que se guarda con completed = false
    }

    @Test
    void updateStatus_WithFalse_ShouldSetCompletedToFalse() {
        // Given
        TaskStatusUpdateDTO falseStatus = new TaskStatusUpdateDTO(false);
        
        Task updatedTask = new Task(
            taskId,
            "Tarea de prueba",
            "Descripción de la tarea",
            false, // Cambiado a false
            LocalDateTime.now().plusDays(1),
            todoListId
        );
        
        TaskResponseDTO updatedResponse = new TaskResponseDTO(
            taskId,
            "Tarea de prueba",
            "Descripción de la tarea",
            false, // Cambiado a false
            LocalDateTime.now().plusDays(1),
            todoListId
        );
        
        when(repo.findById(taskId)).thenReturn(task);
        when(repo.save(task)).thenReturn(updatedTask);
        when(mapper.toResponseDTO(updatedTask)).thenReturn(updatedResponse);

        // When
        TaskResponseDTO result = service.updateStatus(taskId, falseStatus);

        // Then
        assertNotNull(result);
        assertFalse(result.completed()); // Debe ser false

        verify(repo).findById(taskId);
        verify(repo).save(task);
    }
} 