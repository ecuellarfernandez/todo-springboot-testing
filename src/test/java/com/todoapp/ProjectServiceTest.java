package com.todoapp;

import com.todoapp.common.OwnershipValidator;
import com.todoapp.common.UserProvider;
import com.todoapp.common.exception.OwnershipException;
import com.todoapp.project.application.ProjectService;
import com.todoapp.project.application.mapper.ProjectMapper;
import com.todoapp.project.domain.Project;
import com.todoapp.project.dto.ProjectResponseDTO;
import com.todoapp.project.dto.ProjectUpdateDTO;
import com.todoapp.project.port.out.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.doThrow;


import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    void shouldThrowWhenProjectNotFound() {
        UUID projectId = UUID.randomUUID();

        when(repository.findById(projectId)).thenThrow(new NoSuchElementException("Proyecto no existe"));

        assertThrows(NoSuchElementException.class, () -> {
            service.getById(projectId);
        });
        verify(repository).findById(projectId);
    }

    @Test
    void shouldThrowIfUserIsNotOwner() {
        UUID projectId = UUID.randomUUID();
        Project project = new Project
                (projectId,
                        "Test Project",
                        "Description",
                        UUID.randomUUID(),
                        null
                );
        when(repository.findById(projectId)).thenReturn(project); //simula que el encont el proyecto


        doThrow(new OwnershipException("No autorizado")) //si el user no es el dueño del proyect
                .when(ownershipValidator).validateProjectOwnership(projectId);

        assertThrows(OwnershipException.class, () -> {
            service.getById(projectId);
        });
        verify(ownershipValidator).validateProjectOwnership(projectId);
    }


    @Test
    void shouldUpdateProjectWhenUserIsOwner() {
        UUID projectId = UUID.randomUUID();
        Project existing = new Project(projectId, "Old Name", "Old Desc", UUID.randomUUID(), null);
        ProjectUpdateDTO updateDTO = new ProjectUpdateDTO("New Name", null);

        Project updated = new Project(projectId, "New Name", "Old Desc", existing.getUserId(), null);
        ProjectResponseDTO expected = new ProjectResponseDTO(projectId, "New Name", "Old Desc", null, null);

        when(repository.findById(projectId)).thenReturn(existing);
        when(repository.save(existing)).thenReturn(updated);
        when(mapper.toResponseDTO(updated)).thenReturn(expected);

        ProjectResponseDTO result = service.update(projectId, updateDTO);

        assertEquals(expected, result);
        verify(ownershipValidator).validateProjectOwnership(projectId);
        verify(repository).save(existing);
    }
    @Test
    void shouldDeleteProjectWhenUserIsOwner() {
        UUID projectId = UUID.randomUUID();
        Project project = new Project(projectId, "ToDelete", "Desc", UUID.randomUUID(), null);

        when(repository.findById(projectId)).thenReturn(project);
        doNothing().when(ownershipValidator).validateProjectOwnership(projectId);
        doNothing().when(repository).delete(projectId);

        service.delete(projectId);

        verify(ownershipValidator).validateProjectOwnership(projectId);
        verify(repository).delete(projectId);
    }

}