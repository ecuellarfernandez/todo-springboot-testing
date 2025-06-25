package com.todoapp.task.adapter.in;

import com.todoapp.task.dto.*;
import com.todoapp.task.port.in.TaskUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@ContextConfiguration(classes = {TaskController.class, TaskControllerTest.TestConfig.class})
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskUseCase taskUseCase;

    private final UUID taskId = UUID.randomUUID();
    private final UUID todoListId = UUID.randomUUID();
    private final UUID projectId = UUID.randomUUID();
    private final LocalDate dueDate = LocalDate.now().plusDays(7);

    @Test
    void shouldCreateTaskSuccessfully() throws Exception {
        TaskResponseDTO response = new TaskResponseDTO(
                taskId, "Test Task", "Test Description", "false", dueDate, todoListId, projectId
        );

        when(taskUseCase.create(any(TaskCreateDTO.class)))
                .thenReturn(response);

        String json = """
            {"title": "Test Task", "description": "Test Description", "dueDate": "%s"}
        """.formatted(dueDate);

        mockMvc.perform(post("/api/projects/" + projectId + "/todolists/" + todoListId + "/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    void shouldReturn400OnInvalidTaskData() throws Exception {
        String json = """
            {"title": "", "description": "Test Description"}
        """;

        mockMvc.perform(post("/api/projects/" + projectId + "/todolists/" + todoListId + "/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetTaskByIdSuccessfully() throws Exception {
        TaskResponseDTO response = new TaskResponseDTO(
                taskId, "Test Task", "Test Description", "false", dueDate, todoListId, projectId
        );

        when(taskUseCase.getById(taskId, todoListId, projectId))
                .thenReturn(response);

        mockMvc.perform(get("/api/projects/" + projectId + "/todolists/" + todoListId + "/tasks/" + taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(taskId.toString()))
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    void shouldGetTasksByTodoListSuccessfully() throws Exception {
        TaskResponseDTO task1 = new TaskResponseDTO(
                taskId, "Task 1", "Description 1", "false", dueDate, todoListId, projectId
        );
        TaskResponseDTO task2 = new TaskResponseDTO(
                UUID.randomUUID(), "Task 2", "Description 2", "true", dueDate, todoListId, projectId
        );

        when(taskUseCase.getByTodoListId(todoListId, projectId))
                .thenReturn(Arrays.asList(task1, task2));

        mockMvc.perform(get("/api/projects/" + projectId + "/todolists/" + todoListId + "/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("Task 1"))
                .andExpect(jsonPath("$[1].title").value("Task 2"));
    }

    @Test
    void shouldUpdateTaskSuccessfully() throws Exception {
        TaskResponseDTO response = new TaskResponseDTO(
                taskId, "Updated Task", "Updated Description", "false", dueDate, todoListId, projectId
        );

        when(taskUseCase.update(eq(taskId), any(TaskUpdateDTO.class), eq(todoListId), eq(projectId)))
                .thenReturn(response);

        String json = """
            {"title": "Updated Task", "description": "Updated Description", "dueDate": "%s"}
        """.formatted(dueDate);

        mockMvc.perform(put("/api/projects/" + projectId + "/todolists/" + todoListId + "/tasks/" + taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Task"))
                .andExpect(jsonPath("$.description").value("Updated Description"));
    }

    @Test
    void shouldUpdateTaskStatusSuccessfully() throws Exception {
        TaskResponseDTO response = new TaskResponseDTO(
                taskId, "Test Task", "Test Description", "true", dueDate, todoListId, projectId
        );

        when(taskUseCase.updateStatus(eq(taskId), any(TaskStatusUpdateDTO.class), eq(todoListId), eq(projectId)))
                .thenReturn(response);

        String json = """
            {"completed": true}
        """;

        mockMvc.perform(patch("/api/projects/" + projectId + "/todolists/" + todoListId + "/tasks/" + taskId + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value("true"));
    }

    @Test
    void shouldDeleteTaskSuccessfully() throws Exception {
        Mockito.doNothing().when(taskUseCase).delete(taskId, todoListId, projectId);

        mockMvc.perform(delete("/api/projects/" + projectId + "/todolists/" + todoListId + "/tasks/" + taskId))
                .andExpect(status().isNoContent());
    }

    @Configuration
    static class TestConfig {
        
        @Bean
        public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
            http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                    .anyRequest().permitAll()
                );
            
            return http.build();
        }
    }
} 