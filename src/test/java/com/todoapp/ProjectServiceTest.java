package com.todoapp;

import com.todoapp.common.OwnershipValidator;
import com.todoapp.common.UserProvider;
import com.todoapp.project.application.ProjectService;
import com.todoapp.project.application.mapper.ProjectMapper;
import com.todoapp.project.domain.Project;
import com.todoapp.project.dto.ProjectResponseDTO;
import com.todoapp.project.port.out.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    @Mock
    private ProjectRepository repository;
    @Mock
    private ProjectMapper mapper;
    @Mock
    private OwnershipValidator ownershipValidator;
    @Mock
    private UserProvider userProvider;
    @InjectMocks
    private ProjectService service;

    @Test
    public void shouldReturnProjectWhenUserOwnsIt() {

        UUID projectId = UUID.randomUUID();
        Project project = new Project(projectId, "Test Project", "Description", UUID.randomUUID(), null);
        ProjectResponseDTO expectedResponse = new ProjectResponseDTO(
                projectId,
                "Test Project",
                "Description",
                null,
                null
        );

        when(repository.findById(projectId)).thenReturn(project);
        when(mapper.toResponseDTO(project)).thenReturn(expectedResponse);
        doNothing().when(ownershipValidator).validateProjectOwnership(projectId);

        ProjectResponseDTO result = service.getById(projectId);

        assertEquals(expectedResponse, result);
        verify(ownershipValidator).validateProjectOwnership(projectId);
    }
}