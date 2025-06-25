package com.todoapp.project.adapter.out;

import com.todoapp.project.application.mapper.ProjectMapper;
import com.todoapp.project.domain.Project;
import com.todoapp.user.adapter.out.UserEntity;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectRepositoryImplTest {

    @Mock
    private ProjectJpaRepository jpaRepository;

    @Mock
    private ProjectMapper mapper;

    @Mock
    private EntityManager entityManager;

    private ProjectRepositoryImpl repository;

    private final UUID projectId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();
    private final LocalDateTime createdAt = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        repository = new ProjectRepositoryImpl(jpaRepository, mapper);
        // Inject EntityManager using reflection since it's annotated with @PersistenceContext
        try {
            var field = ProjectRepositoryImpl.class.getDeclaredField("entityManager");
            field.setAccessible(true);
            field.set(repository, entityManager);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void shouldSaveProjectSuccessfully() {
        // Given
        Project project = new Project(projectId, "Test Project", "Test Description", userId, createdAt);
        ProjectEntity entity = new ProjectEntity(projectId, "Test Project", "Test Description", null);
        ProjectEntity savedEntity = new ProjectEntity(projectId, "Test Project", "Test Description", null);
        UserEntity userEntity = new UserEntity();

        when(mapper.domainToEntity(project)).thenReturn(entity);
        when(entityManager.getReference(UserEntity.class, userId)).thenReturn(userEntity);
        when(jpaRepository.save(entity)).thenReturn(savedEntity);
        when(mapper.entityToDomain(savedEntity)).thenReturn(project);

        // When
        Project result = repository.save(project);

        // Then
        assertThat(result).isEqualTo(project);
        verify(mapper).domainToEntity(project);
        verify(entityManager).getReference(UserEntity.class, userId);
        verify(jpaRepository).save(entity);
        verify(mapper).entityToDomain(savedEntity);
    }

    @Test
    void shouldFindProjectByIdSuccessfully() {
        // Given
        Project project = new Project(projectId, "Test Project", "Test Description", userId, createdAt);
        ProjectEntity entity = new ProjectEntity(projectId, "Test Project", "Test Description", null);

        when(jpaRepository.findById(projectId)).thenReturn(Optional.of(entity));
        when(mapper.entityToDomain(entity)).thenReturn(project);

        // When
        Project result = repository.findById(projectId);

        // Then
        assertThat(result).isEqualTo(project);
        verify(jpaRepository).findById(projectId);
        verify(mapper).entityToDomain(entity);
    }

    @Test
    void shouldThrowExceptionWhenProjectNotFound() {
        // Given
        when(jpaRepository.findById(projectId)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> repository.findById(projectId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Proyecto no encontrado con id: " + projectId);
    }

    @Test
    void shouldFindProjectsByUserIdSuccessfully() {
        // Given
        Project project1 = new Project(projectId, "Project 1", "Description 1", userId, createdAt);
        Project project2 = new Project(UUID.randomUUID(), "Project 2", "Description 2", userId, createdAt);
        ProjectEntity entity1 = new ProjectEntity(projectId, "Project 1", "Description 1", null);
        ProjectEntity entity2 = new ProjectEntity(UUID.randomUUID(), "Project 2", "Description 2", null);

        List<ProjectEntity> entities = Arrays.asList(entity1, entity2);
        List<Project> projects = Arrays.asList(project1, project2);

        when(jpaRepository.findByOwnerId(userId)).thenReturn(entities);
        when(mapper.entitiesToDomains(entities)).thenReturn(projects);

        // When
        List<Project> result = repository.findByUserId(userId);

        // Then
        assertThat(result).isEqualTo(projects);
        verify(jpaRepository).findByOwnerId(userId);
        verify(mapper).entitiesToDomains(entities);
    }

    @Test
    void shouldThrowExceptionWhenNoProjectsFoundForUser() {
        // Given
        when(jpaRepository.findByOwnerId(userId)).thenReturn(Arrays.asList());

        // When & Then
        assertThatThrownBy(() -> repository.findByUserId(userId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("No se encontraron proyectos para el usuario con id: " + userId);
    }

    @Test
    void shouldDeleteProjectSuccessfully() {
        // Given
        when(jpaRepository.existsById(projectId)).thenReturn(true);
        doNothing().when(jpaRepository).deleteById(projectId);

        // When
        repository.delete(projectId);

        // Then
        verify(jpaRepository).existsById(projectId);
        verify(jpaRepository).deleteById(projectId);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentProject() {
        // Given
        when(jpaRepository.existsById(projectId)).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> repository.delete(projectId))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("No se encontró el proyecto con id: " + projectId);
    }

    @Test
    void shouldCheckIfProjectExists() {
        // Given
        when(jpaRepository.existsById(projectId)).thenReturn(true);

        // When
        boolean result = repository.existsById(projectId);

        // Then
        assertThat(result).isTrue();
        verify(jpaRepository).existsById(projectId);
    }

    @Test
    void shouldReturnFalseWhenProjectDoesNotExist() {
        // Given
        when(jpaRepository.existsById(projectId)).thenReturn(false);

        // When
        boolean result = repository.existsById(projectId);

        // Then
        assertThat(result).isFalse();
        verify(jpaRepository).existsById(projectId);
    }
} 