package com.todoapp.task.adapter.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.todoapp.task.dto.*;
import com.todoapp.task.port.in.TaskUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskUseCase useCase;

    @InjectMocks
    private TaskController controller;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private UUID taskId;
    private UUID todoListId;
    private TaskRequestDTO taskRequestDTO;
    private TaskResponseDTO taskResponseDTO;
    private TaskUpdateDTO taskUpdateDTO;
    private TaskStatusUpdateDTO taskStatusUpdateDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        
        taskId = UUID.randomUUID();
        todoListId = UUID.randomUUID();
        
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
    void shouldCreateTaskSuccessfully() throws Exception {
        // Given
        when(useCase.create(any(TaskRequestDTO.class))).thenReturn(taskResponseDTO);

        // When & Then
        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(taskId.toString()))
                .andExpect(jsonPath("$.title").value("Tarea de prueba"))
                .andExpect(jsonPath("$.description").value("Descripción de la tarea"))
                .andExpect(jsonPath("$.completed").value(false))
                .andExpect(jsonPath("$.todoListId").value(todoListId.toString()));

        verify(useCase).create(any(TaskRequestDTO.class));
    }

    @Test
    void shouldCreateTaskWithNullDescription() throws Exception {
        // Given
        TaskRequestDTO requestWithNullDescription = new TaskRequestDTO(
            "Tarea sin descripción",
            null,
            LocalDateTime.now().plusDays(1),
            todoListId
        );
        
        TaskResponseDTO responseWithNullDescription = new TaskResponseDTO(
            taskId,
            "Tarea sin descripción",
            null,
            false,
            LocalDateTime.now().plusDays(1),
            todoListId
        );
        
        when(useCase.create(any(TaskRequestDTO.class))).thenReturn(responseWithNullDescription);

        // When & Then
        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestWithNullDescription)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(taskId.toString()))
                .andExpect(jsonPath("$.title").value("Tarea sin descripción"))
                .andExpect(jsonPath("$.description").isEmpty())
                .andExpect(jsonPath("$.completed").value(false));

        verify(useCase).create(any(TaskRequestDTO.class));
    }

    @Test
    void shouldCreateTaskWithNullDueDate() throws Exception {
        // Given
        TaskRequestDTO requestWithNullDueDate = new TaskRequestDTO(
            "Tarea sin fecha",
            "Descripción",
            null,
            todoListId
        );
        
        TaskResponseDTO responseWithNullDueDate = new TaskResponseDTO(
            taskId,
            "Tarea sin fecha",
            "Descripción",
            false,
            null,
            todoListId
        );
        
        when(useCase.create(any(TaskRequestDTO.class))).thenReturn(responseWithNullDueDate);

        // When & Then
        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestWithNullDueDate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(taskId.toString()))
                .andExpect(jsonPath("$.title").value("Tarea sin fecha"))
                .andExpect(jsonPath("$.description").value("Descripción"))
                .andExpect(jsonPath("$.dueDate").isEmpty())
                .andExpect(jsonPath("$.completed").value(false));

        verify(useCase).create(any(TaskRequestDTO.class));
    }

    @Test
    void shouldReturnBadRequestWhenCreatingTaskWithInvalidData() throws Exception {
        // Given - Enviar JSON malformado para probar validación básica
        String invalidJson = "{ invalid json }";

        // When & Then
        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(status().isBadRequest());

        verify(useCase, never()).create(any());
    }

    @Test
    void shouldGetTaskByIdSuccessfully() throws Exception {
        // Given
        when(useCase.getById(taskId)).thenReturn(taskResponseDTO);

        // When & Then
        mockMvc.perform(get("/api/tasks/{id}", taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskId.toString()))
                .andExpect(jsonPath("$.title").value("Tarea de prueba"))
                .andExpect(jsonPath("$.description").value("Descripción de la tarea"))
                .andExpect(jsonPath("$.completed").value(false))
                .andExpect(jsonPath("$.todoListId").value(todoListId.toString()));

        verify(useCase).getById(taskId);
    }

    @Test
    void shouldGetTasksByTodoListSuccessfully() throws Exception {
        // Given
        TaskResponseDTO task1 = new TaskResponseDTO(
            UUID.randomUUID(),
            "Tarea 1",
            "Descripción 1",
            false,
            LocalDateTime.now().plusDays(1),
            todoListId
        );
        
        TaskResponseDTO task2 = new TaskResponseDTO(
            UUID.randomUUID(),
            "Tarea 2",
            "Descripción 2",
            true,
            LocalDateTime.now().plusDays(2),
            todoListId
        );
        
        List<TaskResponseDTO> tasks = Arrays.asList(task1, task2);
        
        when(useCase.getByTodoList(todoListId)).thenReturn(tasks);

        // When & Then
        mockMvc.perform(get("/api/tasks/list/{todoListId}", todoListId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Tarea 1"))
                .andExpect(jsonPath("$[0].completed").value(false))
                .andExpect(jsonPath("$[1].title").value("Tarea 2"))
                .andExpect(jsonPath("$[1].completed").value(true))
                .andExpect(jsonPath("$[0].todoListId").value(todoListId.toString()))
                .andExpect(jsonPath("$[1].todoListId").value(todoListId.toString()));

        verify(useCase).getByTodoList(todoListId);
    }

    @Test
    void shouldReturnEmptyListWhenNoTasksForTodoList() throws Exception {
        // Given
        when(useCase.getByTodoList(todoListId)).thenReturn(Arrays.asList());

        // When & Then
        mockMvc.perform(get("/api/tasks/list/{todoListId}", todoListId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(useCase).getByTodoList(todoListId);
    }

    @Test
    void shouldUpdateTaskSuccessfully() throws Exception {
        // Given
        TaskResponseDTO updatedResponse = new TaskResponseDTO(
            taskId,
            "Tarea actualizada",
            "Descripción actualizada",
            false,
            LocalDateTime.now().plusDays(2),
            todoListId
        );
        
        when(useCase.update(eq(taskId), any(TaskUpdateDTO.class))).thenReturn(updatedResponse);

        // When & Then
        mockMvc.perform(put("/api/tasks/{id}", taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskId.toString()))
                .andExpect(jsonPath("$.title").value("Tarea actualizada"))
                .andExpect(jsonPath("$.description").value("Descripción actualizada"))
                .andExpect(jsonPath("$.completed").value(false));

        verify(useCase).update(eq(taskId), any(TaskUpdateDTO.class));
    }

    @Test
    void shouldUpdateTaskWithPartialData() throws Exception {
        // Given
        TaskUpdateDTO partialUpdateDTO = new TaskUpdateDTO("Solo título", null, null);
        
        TaskResponseDTO partialResponse = new TaskResponseDTO(
            taskId,
            "Solo título",
            "Descripción original",
            false,
            LocalDateTime.now().plusDays(1),
            todoListId
        );
        
        when(useCase.update(eq(taskId), any(TaskUpdateDTO.class))).thenReturn(partialResponse);

        // When & Then
        mockMvc.perform(put("/api/tasks/{id}", taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(partialUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskId.toString()))
                .andExpect(jsonPath("$.title").value("Solo título"))
                .andExpect(jsonPath("$.description").value("Descripción original"))
                .andExpect(jsonPath("$.completed").value(false));

        verify(useCase).update(eq(taskId), any(TaskUpdateDTO.class));
    }

    @Test
    void shouldReturnBadRequestWhenUpdatingTaskWithInvalidData() throws Exception {
        // Given - Enviar JSON malformado para probar validación básica
        String invalidJson = "{ invalid json }";

        // When & Then
        mockMvc.perform(put("/api/tasks/{id}", taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(status().isBadRequest());

        verify(useCase, never()).update(any(), any());
    }

    @Test
    void shouldUpdateTaskStatusSuccessfully() throws Exception {
        // Given
        TaskResponseDTO completedResponse = new TaskResponseDTO(
            taskId,
            "Tarea de prueba",
            "Descripción de la tarea",
            true, // Updated to completed
            LocalDateTime.now().plusDays(1),
            todoListId
        );
        
        when(useCase.updateStatus(eq(taskId), any(TaskStatusUpdateDTO.class))).thenReturn(completedResponse);

        // When & Then
        mockMvc.perform(patch("/api/tasks/{id}/status", taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(taskStatusUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskId.toString()))
                .andExpect(jsonPath("$.title").value("Tarea de prueba"))
                .andExpect(jsonPath("$.description").value("Descripción de la tarea"))
                .andExpect(jsonPath("$.completed").value(true));

        verify(useCase).updateStatus(eq(taskId), any(TaskStatusUpdateDTO.class));
    }

    @Test
    void shouldUpdateTaskStatusToIncomplete() throws Exception {
        // Given
        TaskStatusUpdateDTO incompleteStatusDTO = new TaskStatusUpdateDTO(false);
        
        TaskResponseDTO incompleteResponse = new TaskResponseDTO(
            taskId,
            "Tarea de prueba",
            "Descripción de la tarea",
            false, // Updated to incomplete
            LocalDateTime.now().plusDays(1),
            todoListId
        );
        
        when(useCase.updateStatus(eq(taskId), any(TaskStatusUpdateDTO.class))).thenReturn(incompleteResponse);

        // When & Then
        mockMvc.perform(patch("/api/tasks/{id}/status", taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(incompleteStatusDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskId.toString()))
                .andExpect(jsonPath("$.title").value("Tarea de prueba"))
                .andExpect(jsonPath("$.description").value("Descripción de la tarea"))
                .andExpect(jsonPath("$.completed").value(false));

        verify(useCase).updateStatus(eq(taskId), any(TaskStatusUpdateDTO.class));
    }

    @Test
    void shouldDeleteTaskSuccessfully() throws Exception {
        // Given
        doNothing().when(useCase).delete(taskId);

        // When & Then
        mockMvc.perform(delete("/api/tasks/{id}", taskId))
                .andExpect(status().isNoContent());

        verify(useCase).delete(taskId);
    }

    @Test
    void shouldHandleTaskWithSpecialCharacters() throws Exception {
        // Given
        TaskRequestDTO specialRequestDTO = new TaskRequestDTO(
            "Tarea con caracteres especiales: áéíóú ñ @#$%^&*()",
            "Descripción con símbolos: ©®™ €£¥ ¢∞§¶•ªº",
            LocalDateTime.now().plusDays(1),
            todoListId
        );
        
        TaskResponseDTO specialResponse = new TaskResponseDTO(
            taskId,
            "Tarea con caracteres especiales: áéíóú ñ @#$%^&*()",
            "Descripción con símbolos: ©®™ €£¥ ¢∞§¶•ªº",
            false,
            LocalDateTime.now().plusDays(1),
            todoListId
        );
        
        when(useCase.create(any(TaskRequestDTO.class))).thenReturn(specialResponse);

        // When & Then
        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(specialRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(taskId.toString()))
                .andExpect(jsonPath("$.title").value("Tarea con caracteres especiales: áéíóú ñ @#$%^&*()"))
                .andExpect(jsonPath("$.description").value("Descripción con símbolos: ©®™ €£¥ ¢∞§¶•ªº"))
                .andExpect(jsonPath("$.completed").value(false));

        verify(useCase).create(any(TaskRequestDTO.class));
    }

    @Test
    void shouldHandleTaskWithUnicodeCharacters() throws Exception {
        // Given
        TaskRequestDTO unicodeRequestDTO = new TaskRequestDTO(
            "Tarea con emojis 🚀📝✅",
            "Descripción con unicode: 🌟✨💫",
            LocalDateTime.now().plusDays(1),
            todoListId
        );
        
        TaskResponseDTO unicodeResponse = new TaskResponseDTO(
            taskId,
            "Tarea con emojis 🚀📝✅",
            "Descripción con unicode: 🌟✨💫",
            false,
            LocalDateTime.now().plusDays(1),
            todoListId
        );
        
        when(useCase.create(any(TaskRequestDTO.class))).thenReturn(unicodeResponse);

        // When & Then
        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unicodeRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(taskId.toString()))
                .andExpect(jsonPath("$.title").value("Tarea con emojis 🚀📝✅"))
                .andExpect(jsonPath("$.description").value("Descripción con unicode: 🌟✨💫"))
                .andExpect(jsonPath("$.completed").value(false));

        verify(useCase).create(any(TaskRequestDTO.class));
    }

    @Test
    void shouldHandleTaskWithLongStrings() throws Exception {
        // Given
        String longTitle = "A".repeat(1000);
        String longDescription = "B".repeat(2000);
        
        TaskRequestDTO longRequestDTO = new TaskRequestDTO(
            longTitle,
            longDescription,
            LocalDateTime.now().plusDays(1),
            todoListId
        );
        
        TaskResponseDTO longResponse = new TaskResponseDTO(
            taskId,
            longTitle,
            longDescription,
            false,
            LocalDateTime.now().plusDays(1),
            todoListId
        );
        
        when(useCase.create(any(TaskRequestDTO.class))).thenReturn(longResponse);

        // When & Then
        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(longRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(taskId.toString()))
                .andExpect(jsonPath("$.title").value(longTitle))
                .andExpect(jsonPath("$.description").value(longDescription))
                .andExpect(jsonPath("$.completed").value(false));

        verify(useCase).create(any(TaskRequestDTO.class));
    }

    @Test
    void shouldHandleInvalidUUIDInPath() throws Exception {
        // Given
        String invalidUUID = "invalid-uuid";

        // When & Then
        mockMvc.perform(get("/api/tasks/{id}", invalidUUID))
                .andExpect(status().isBadRequest());

        verify(useCase, never()).getById(any());
    }

    @Test
    void shouldHandleInvalidUUIDInTodoListPath() throws Exception {
        // Given
        String invalidUUID = "invalid-uuid";

        // When & Then
        mockMvc.perform(get("/api/tasks/list/{todoListId}", invalidUUID))
                .andExpect(status().isBadRequest());

        verify(useCase, never()).getByTodoList(any());
    }

    @Test
    void shouldHandleNullBodyInCreate() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(useCase, never()).create(any());
    }

    @Test
    void shouldHandleNullBodyInUpdate() throws Exception {
        // When & Then
        mockMvc.perform(put("/api/tasks/{id}", taskId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(useCase, never()).update(any(), any());
    }

    @Test
    void shouldHandleNullBodyInUpdateStatus() throws Exception {
        // When & Then
        mockMvc.perform(patch("/api/tasks/{id}/status", taskId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(useCase, never()).updateStatus(any(), any());
    }
} 