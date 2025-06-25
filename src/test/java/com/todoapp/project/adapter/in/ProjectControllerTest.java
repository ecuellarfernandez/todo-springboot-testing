package com.todoapp.project.adapter.in;

import com.todoapp.project.dto.ProjectRequestDTO;
import com.todoapp.project.dto.ProjectResponseDTO;
import com.todoapp.project.dto.ProjectUpdateDTO;
import com.todoapp.project.port.in.ProjectUseCase;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
@ContextConfiguration(classes = {ProjectController.class, ProjectControllerTest.TestConfig.class})
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectUseCase projectUseCase;

    private final UUID projectId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Test
    void shouldCreateProjectSuccessfully() throws Exception {
        ProjectResponseDTO response = new ProjectResponseDTO(projectId, "Test Project", "Test Description", userId, createdAt);

        when(projectUseCase.create(any(ProjectRequestDTO.class)))
                .thenReturn(response);

        String json = """
            {"name": "Test Project", "description": "Test Description"}
        """;

        mockMvc.perform(post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Test Project"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    void shouldReturn400OnInvalidProjectData() throws Exception {
        String json = """
            {"name": "", "description": "Test Description"}
        """;

        mockMvc.perform(post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetProjectByIdSuccessfully() throws Exception {
        ProjectResponseDTO response = new ProjectResponseDTO(projectId, "Test Project", "Test Description", userId, createdAt);

        when(projectUseCase.getById(projectId))
                .thenReturn(response);

        mockMvc.perform(get("/api/projects/" + projectId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(projectId.toString()))
                .andExpect(jsonPath("$.name").value("Test Project"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    void shouldGetProjectsByUserSuccessfully() throws Exception {
        ProjectResponseDTO project1 = new ProjectResponseDTO(projectId, "Project 1", "Description 1", userId, createdAt);
        ProjectResponseDTO project2 = new ProjectResponseDTO(UUID.randomUUID(), "Project 2", "Description 2", userId, createdAt);

        when(projectUseCase.getByUser())
                .thenReturn(Arrays.asList(project1, project2));

        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Project 1"))
                .andExpect(jsonPath("$[1].name").value("Project 2"));
    }

    @Test
    void shouldUpdateProjectSuccessfully() throws Exception {
        ProjectResponseDTO response = new ProjectResponseDTO(projectId, "Updated Project", "Updated Description", userId, createdAt);

        when(projectUseCase.update(eq(projectId), any(ProjectUpdateDTO.class)))
                .thenReturn(response);

        String json = """
            {"name": "Updated Project", "description": "Updated Description"}
        """;

        mockMvc.perform(put("/api/projects/" + projectId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Project"))
                .andExpect(jsonPath("$.description").value("Updated Description"));
    }

    @Test
    void shouldDeleteProjectSuccessfully() throws Exception {
        Mockito.doNothing().when(projectUseCase).delete(projectId);

        mockMvc.perform(delete("/api/projects/" + projectId))
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