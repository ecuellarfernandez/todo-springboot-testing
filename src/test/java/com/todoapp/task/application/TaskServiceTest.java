package com.todoapp.task.application;

import com.todoapp.common.OwnershipValidator;
import com.todoapp.project.domain.Project;
import com.todoapp.project.port.out.ProjectRepository;
import com.todoapp.task.application.mapper.TaskMapper;
import com.todoapp.task.domain.Task;
import com.todoapp.task.dto.*;
import com.todoapp.task.port.out.TaskRepository;
import com.todoapp.todolist.domain.TodoList;
import com.todoapp.todolist.port.out.TodoListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;

    @Mock
    TaskMapper taskMapper;

    @Mock
    OwnershipValidator ownershipValidator;

    @Mock
    TodoListRepository todoListRepository;

    @Mock
    ProjectRepository projectRepository;

    TaskService taskService;

    private final UUID taskId = UUID.randomUUID();
    private final UUID todoListId = UUID.randomUUID();
    private final UUID projectId = UUID.randomUUID();
    private final LocalDate dueDate = LocalDate.now().plusDays(7);

    @BeforeEach
    void setUp() {
        taskService = new TaskService(taskRepository, taskMapper, ownershipValidator, todoListRepository, projectRepository);
    }

    @Test
    void shouldCreateTaskSuccessfully() {
        // Given
        TaskCreateDTO createDTO = new TaskCreateDTO("Test Task", "Test Description", dueDate, todoListId, projectId);
        Task task = new Task(taskId, "Test Task", "Test Description", false, dueDate, todoListId);
        TaskResponseDTO expectedResponse = new TaskResponseDTO(taskId, "Test Task", "Test Description", false, dueDate, todoListId, projectId, 0);

        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(taskMapper.toResponseDTO(task)).thenReturn(expectedResponse);
        doNothing().when(ownershipValidator).validateTodoListOwnership(todoListId, projectId);

        // When
        TaskResponseDTO result = taskService.create(createDTO);

        // Then
        assertThat(result).isEqualTo(expectedResponse);
        verify(taskRepository).save(any(Task.class));
        verify(ownershipValidator).validateTodoListOwnership(todoListId, projectId);
    }

    @Test
    void shouldGetTaskByIdSuccessfully() {
        // Given
        Task task = new Task(taskId, "Test Task", "Test Description", false, dueDate, todoListId);
        TaskResponseDTO expectedResponse = new TaskResponseDTO(taskId, "Test Task", "Test Description", false, dueDate, todoListId, projectId, 0);

        when(taskRepository.existsById(taskId)).thenReturn(true);
        when(taskRepository.findById(taskId)).thenReturn(task);
        when(taskMapper.toResponseDTO(task)).thenReturn(expectedResponse);
        doNothing().when(ownershipValidator).validateTodoListOwnership(todoListId, projectId);

        // When
        TaskResponseDTO result = taskService.getById(taskId, todoListId, projectId);

        // Then
        assertThat(result).isEqualTo(expectedResponse);
        verify(taskRepository).existsById(taskId);
        verify(taskRepository).findById(taskId);
        verify(ownershipValidator).validateTodoListOwnership(todoListId, projectId);
    }

    @Test
    void shouldThrowExceptionWhenTaskNotFound() {
        // Given
        when(taskRepository.existsById(taskId)).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> taskService.getById(taskId, todoListId, projectId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Task with ID " + taskId + " does not exist");
    }

    @Test
    void shouldThrowExceptionWhenTaskDoesNotBelongToTodoList() {
        // Given
        UUID differentTodoListId = UUID.randomUUID();
        Task task = new Task(taskId, "Test Task", "Test Description", false, dueDate, differentTodoListId);

        when(taskRepository.existsById(taskId)).thenReturn(true);
        when(taskRepository.findById(taskId)).thenReturn(task);

        // When & Then
        assertThatThrownBy(() -> taskService.getById(taskId, todoListId, projectId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("La tarea no pertenece a la lista de tareas especificada");
    }

    @Test
    void shouldGetTasksByTodoListIdSuccessfully() {
        // Given
        Task task1 = new Task(taskId, "Task 1", "Description 1", false, dueDate, todoListId);
        Task task2 = new Task(UUID.randomUUID(), "Task 2", "Description 2", true, dueDate, todoListId);
        TaskResponseDTO response1 = new TaskResponseDTO(taskId, "Task 1", "Description 1", false, dueDate, todoListId, projectId, 0);
        TaskResponseDTO response2 = new TaskResponseDTO(UUID.randomUUID(), "Task 2", "Description 2", true, dueDate, todoListId, projectId, 1);

        when(taskRepository.findByTodoListId(todoListId)).thenReturn(Arrays.asList(task1, task2));
        when(taskMapper.toResponseDTO(task1)).thenReturn(response1);
        when(taskMapper.toResponseDTO(task2)).thenReturn(response2);
        doNothing().when(ownershipValidator).validateTodoListOwnership(todoListId, projectId);

        // When
        List<TaskResponseDTO> result = taskService.getByTodoListId(todoListId, projectId);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(response1, response2);
        verify(taskRepository).findByTodoListId(todoListId);
        verify(ownershipValidator).validateTodoListOwnership(todoListId, projectId);
    }

    @Test
    void shouldUpdateTaskSuccessfully() {
        // Given
        TaskUpdateDTO updateDTO = new TaskUpdateDTO("Updated Task", "Updated Description", dueDate);
        Task task = new Task(taskId, "Original Task", "Original Description", false, dueDate, todoListId);
        Task updatedTask = new Task(taskId, "Updated Task", "Updated Description", false, dueDate, todoListId);
        TaskResponseDTO expectedResponse = new TaskResponseDTO(taskId, "Updated Task", "Updated Description", false, dueDate, todoListId, projectId, 1);

        when(taskRepository.existsById(taskId)).thenReturn(true);
        when(taskRepository.findById(taskId)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(updatedTask);
        when(taskMapper.toResponseDTO(updatedTask)).thenReturn(expectedResponse);
        doNothing().when(ownershipValidator).validateTodoListOwnership(todoListId, projectId);

        // When
        TaskResponseDTO result = taskService.update(taskId, updateDTO, todoListId, projectId);

        // Then
        assertThat(result).isEqualTo(expectedResponse);
        assertThat(task.getTitle()).isEqualTo("Updated Task");
        assertThat(task.getDescription()).isEqualTo("Updated Description");
        assertThat(task.getDueDate()).isEqualTo(dueDate);
        verify(taskRepository).save(task);
        verify(ownershipValidator).validateTodoListOwnership(todoListId, projectId);
    }

    @Test
    void shouldUpdateTaskStatusSuccessfully() {
        // Given
        TaskStatusUpdateDTO statusDTO = new TaskStatusUpdateDTO(true);
        Task task = new Task(taskId, "Test Task", "Test Description", false, dueDate, todoListId);
        Task updatedTask = new Task(taskId, "Test Task", "Test Description", true, dueDate, todoListId);
        TaskResponseDTO expectedResponse = new TaskResponseDTO(taskId, "Test Task", "Test Description", true, dueDate, todoListId, projectId,1);

        when(taskRepository.existsById(taskId)).thenReturn(true);
        when(taskRepository.findById(taskId)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(updatedTask);
        when(taskMapper.toResponseDTO(updatedTask)).thenReturn(expectedResponse);
        doNothing().when(ownershipValidator).validateTodoListOwnership(todoListId, projectId);

        // When
        TaskResponseDTO result = taskService.updateStatus(taskId, statusDTO, todoListId, projectId);

        // Then
        assertThat(result).isEqualTo(expectedResponse);
        assertThat(task.isCompleted()).isTrue();
        verify(taskRepository).save(task);
        verify(ownershipValidator).validateTodoListOwnership(todoListId, projectId);
    }

    @Test
    void shouldDeleteTaskSuccessfully() {
        // Given
        Task task = new Task(taskId, "Test Task", "Test Description", false, dueDate, todoListId);

        when(taskRepository.existsById(taskId)).thenReturn(true);
        when(taskRepository.findById(taskId)).thenReturn(task);
        doNothing().when(taskRepository).delete(taskId);
        doNothing().when(ownershipValidator).validateTodoListOwnership(todoListId, projectId);

        // When
        taskService.delete(taskId, todoListId, projectId);

        // Then
        verify(taskRepository).findById(taskId);
        verify(taskRepository).delete(taskId);
        verify(ownershipValidator).validateTodoListOwnership(todoListId, projectId);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentTask() {
        // Given
        when(taskRepository.existsById(taskId)).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> taskService.delete(taskId, todoListId, projectId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Task with ID " + taskId + " does not exist");
    }
}
