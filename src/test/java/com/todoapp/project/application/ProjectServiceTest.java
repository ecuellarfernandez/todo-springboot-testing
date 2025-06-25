package com.todoapp.project.application;

import com.todoapp.common.OwnershipValidator;
import com.todoapp.common.UserProvider;
import com.todoapp.project.application.mapper.ProjectMapper;
import com.todoapp.project.domain.Project;
import com.todoapp.project.dto.ProjectRequestDTO;
import com.todoapp.project.dto.ProjectResponseDTO;
import com.todoapp.project.dto.ProjectUpdateDTO;
import com.todoapp.project.port.out.ProjectRepository;
import com.todoapp.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    ProjectRepository repository;

    @Mock
    ProjectMapper mapper;

    @Mock
    OwnershipValidator ownershipValidator;

    @Mock
    UserProvider userProvider;

    ProjectService service;

    private final UUID projectId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();
    private final LocalDateTime createdAt = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        service = new ProjectService(repository, mapper, ownershipValidator, userProvider);
    }

    @Test
    void shouldCreateProjectSuccessfully() {
        // Given
        ProjectRequestDTO request = new ProjectRequestDTO("Test Project", "Test Description");
        User currentUser = new User(userId, "testuser", "Test User", "test@email.com", "hashedpass");
        Project project = new Project(projectId, "Test Project", "Test Description", userId, createdAt);
        ProjectResponseDTO expectedResponse = new ProjectResponseDTO(projectId, "Test Project", "Test Description", userId, createdAt);

        when(userProvider.getCurrentUser()).thenReturn(currentUser);
        when(repository.save(any(Project.class))).thenReturn(project);
        when(mapper.toResponseDTO(project)).thenReturn(expectedResponse);

        // When
        ProjectResponseDTO result = service.create(request);

        // Then
        assertThat(result).isEqualTo(expectedResponse);
        verify(repository).save(any(Project.class));
        verify(mapper).toResponseDTO(project);
    }

    @Test
    void shouldGetProjectByIdSuccessfully() {
        // Given
        Project project = new Project(projectId, "Test Project", "Test Description", userId, createdAt);
        ProjectResponseDTO expectedResponse = new ProjectResponseDTO(projectId, "Test Project", "Test Description", userId, createdAt);

        when(repository.findById(projectId)).thenReturn(project);
        when(mapper.toResponseDTO(project)).thenReturn(expectedResponse);
        doNothing().when(ownershipValidator).validateProjectOwnership(projectId);

        // When
        ProjectResponseDTO result = service.getById(projectId);

        // Then
        assertThat(result).isEqualTo(expectedResponse);
        verify(repository).findById(projectId);
        verify(ownershipValidator).validateProjectOwnership(projectId);
    }

    @Test
    void shouldGetProjectsByUserSuccessfully() {
        // Given
        User currentUser = new User(userId, "testuser", "Test User", "test@email.com", "hashedpass");
        Project project1 = new Project(projectId, "Project 1", "Description 1", userId, createdAt);
        Project project2 = new Project(UUID.randomUUID(), "Project 2", "Description 2", userId, createdAt);
        ProjectResponseDTO response1 = new ProjectResponseDTO(projectId, "Project 1", "Description 1", userId, createdAt);
        ProjectResponseDTO response2 = new ProjectResponseDTO(UUID.randomUUID(), "Project 2", "Description 2", userId, createdAt);

        when(userProvider.getCurrentUser()).thenReturn(currentUser);
        when(repository.findByUserId(userId)).thenReturn(Arrays.asList(project1, project2));
        when(mapper.toResponseDTO(project1)).thenReturn(response1);
        when(mapper.toResponseDTO(project2)).thenReturn(response2);

        // When
        List<ProjectResponseDTO> result = service.getByUser();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(response1, response2);
        verify(repository).findByUserId(userId);
    }

    @Test
    void shouldUpdateProjectSuccessfully() {
        // Given
        ProjectUpdateDTO updateRequest = new ProjectUpdateDTO("Updated Project", "Updated Description");
        Project project = new Project(projectId, "Original Project", "Original Description", userId, createdAt);
        Project updatedProject = new Project(projectId, "Updated Project", "Updated Description", userId, createdAt);
        ProjectResponseDTO expectedResponse = new ProjectResponseDTO(projectId, "Updated Project", "Updated Description", userId, createdAt);

        when(repository.findById(projectId)).thenReturn(project);
        when(repository.save(project)).thenReturn(updatedProject);
        when(mapper.toResponseDTO(updatedProject)).thenReturn(expectedResponse);
        doNothing().when(ownershipValidator).validateProjectOwnership(projectId);

        // When
        ProjectResponseDTO result = service.update(projectId, updateRequest);

        // Then
        assertThat(result).isEqualTo(expectedResponse);
        assertThat(project.getName()).isEqualTo("Updated Project");
        assertThat(project.getDescription()).isEqualTo("Updated Description");
        verify(repository).save(project);
        verify(ownershipValidator).validateProjectOwnership(projectId);
    }

    @Test
    void shouldUpdateProjectWithPartialData() {
        // Given
        ProjectUpdateDTO updateRequest = new ProjectUpdateDTO("Updated Project", null);
        Project project = new Project(projectId, "Original Project", "Original Description", userId, createdAt);
        Project updatedProject = new Project(projectId, "Updated Project", "Original Description", userId, createdAt);
        ProjectResponseDTO expectedResponse = new ProjectResponseDTO(projectId, "Updated Project", "Original Description", userId, createdAt);

        when(repository.findById(projectId)).thenReturn(project);
        when(repository.save(project)).thenReturn(updatedProject);
        when(mapper.toResponseDTO(updatedProject)).thenReturn(expectedResponse);
        doNothing().when(ownershipValidator).validateProjectOwnership(projectId);

        // When
        ProjectResponseDTO result = service.update(projectId, updateRequest);

        // Then
        assertThat(result).isEqualTo(expectedResponse);
        assertThat(project.getName()).isEqualTo("Updated Project");
        assertThat(project.getDescription()).isEqualTo("Original Description");
        verify(repository).save(project);
    }

    @Test
    void shouldDeleteProjectSuccessfully() {
        // Given
        Project project = new Project(projectId, "Test Project", "Test Description", userId, createdAt);

        when(repository.findById(projectId)).thenReturn(project);
        doNothing().when(repository).delete(projectId);
        doNothing().when(ownershipValidator).validateProjectOwnership(projectId);

        // When
        service.delete(projectId);

        // Then
        verify(repository).findById(projectId);
        verify(repository).delete(projectId);
        verify(ownershipValidator).validateProjectOwnership(projectId);
    }
} 