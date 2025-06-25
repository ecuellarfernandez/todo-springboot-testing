package com.todoapp.project.application.mapper;

import com.todoapp.project.adapter.out.ProjectEntity;
import com.todoapp.project.domain.Project;
import com.todoapp.project.dto.ProjectResponseDTO;
import com.todoapp.user.adapter.out.UserEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectMapperTest {

    private final ProjectMapper mapper = Mappers.getMapper(ProjectMapper.class);

    private final UUID projectId = UUID.randomUUID();
    private final UUID userId = UUID.randomUUID();
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Test
    void shouldMapProjectToResponseDTO() {
        // Given
        Project project = new Project(projectId, "Test Project", "Test Description", userId, createdAt);

        // When
        ProjectResponseDTO result = mapper.toResponseDTO(project);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(projectId);
        assertThat(result.name()).isEqualTo("Test Project");
        assertThat(result.description()).isEqualTo("Test Description");
        assertThat(result.userId()).isEqualTo(userId);
        assertThat(result.createdAt()).isEqualTo(createdAt);
    }

    @Test
    void shouldMapProjectToEntity() {
        // Given
        Project project = new Project(projectId, "Test Project", "Test Description", userId, createdAt);

        // When
        ProjectEntity result = mapper.domainToEntity(project);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(projectId);
        assertThat(result.getName()).isEqualTo("Test Project");
        assertThat(result.getDescription()).isEqualTo("Test Description");
        // Owner should be ignored as per mapping configuration
        assertThat(result.getOwner()).isNull();
    }

    @Test
    void shouldMapEntityToProject() {
        // Given
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        
        ProjectEntity entity = new ProjectEntity(projectId, "Test Project", "Test Description", userEntity);
        entity.setCreatedAt(createdAt);

        // When
        Project result = mapper.entityToDomain(entity);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(projectId);
        assertThat(result.getName()).isEqualTo("Test Project");
        assertThat(result.getDescription()).isEqualTo("Test Description");
        assertThat(result.getUserId()).isEqualTo(userId);
        assertThat(result.getCreatedAt()).isEqualTo(createdAt);
    }

    @Test
    void shouldMapEntitiesToDomains() {
        // Given
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        
        ProjectEntity entity1 = new ProjectEntity(projectId, "Project 1", "Description 1", userEntity);
        ProjectEntity entity2 = new ProjectEntity(UUID.randomUUID(), "Project 2", "Description 2", userEntity);
        
        List<ProjectEntity> entities = Arrays.asList(entity1, entity2);

        // When
        List<Project> result = mapper.entitiesToDomains(entities);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Project 1");
        assertThat(result.get(1).getName()).isEqualTo("Project 2");
    }

    @Test
    void shouldHandleNullValuesInProjectToResponseDTO() {
        // Given
        Project project = new Project(projectId, "Test Project", null, userId, null);

        // When
        ProjectResponseDTO result = mapper.toResponseDTO(project);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(projectId);
        assertThat(result.name()).isEqualTo("Test Project");
        assertThat(result.description()).isNull();
        assertThat(result.userId()).isEqualTo(userId);
        assertThat(result.createdAt()).isNull();
    }

    @Test
    void shouldHandleNullValuesInEntityToProject() {
        // Given
        ProjectEntity entity = new ProjectEntity(projectId, "Test Project", null, null);
        entity.setCreatedAt(null);

        // When
        Project result = mapper.entityToDomain(entity);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(projectId);
        assertThat(result.getName()).isEqualTo("Test Project");
        assertThat(result.getDescription()).isNull();
        assertThat(result.getUserId()).isNull();
        assertThat(result.getCreatedAt()).isNull();
    }

    @Test
    void shouldHandleEmptyListInEntitiesToDomains() {
        // Given
        List<ProjectEntity> entities = Arrays.asList();

        // When
        List<Project> result = mapper.entitiesToDomains(entities);

        // Then
        assertThat(result).isEmpty();
    }
} 