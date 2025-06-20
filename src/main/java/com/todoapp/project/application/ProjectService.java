package com.todoapp.project.application;

import com.todoapp.auth.port.in.UserContextUseCase;
import com.todoapp.common.OwnershipValidator;
import com.todoapp.common.UserProvider;
import com.todoapp.project.application.mapper.ProjectMapper;
import com.todoapp.project.domain.Project;
import com.todoapp.project.dto.ProjectRequestDTO;
import com.todoapp.project.dto.ProjectResponseDTO;
import com.todoapp.project.dto.ProjectUpdateDTO;
import com.todoapp.project.port.in.ProjectUseCase;
import com.todoapp.project.port.out.ProjectRepository;
import com.todoapp.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectService implements ProjectUseCase {

    private final ProjectRepository repo;
    private final ProjectMapper mapper;
    private final OwnershipValidator ownershipValidator;
    private final UserProvider userProvider;

    public ProjectService(ProjectRepository repo, ProjectMapper mapper, OwnershipValidator ownershipValidator, UserProvider userProvider) {
        this.repo = repo;
        this.mapper = mapper;
        this.ownershipValidator = ownershipValidator;
        this.userProvider = userProvider;
    }

    @Override
    @Transactional
    public ProjectResponseDTO create(ProjectRequestDTO dto) {
        User currentUser = userProvider.getCurrentUser();

        Project project = new Project(
                null,
                dto.name(),
                dto.description(),
                currentUser.getId(),
                null
        );

        Project saved = repo.save(project);
        return mapper.toResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponseDTO getById(UUID id) {
        Project project = repo.findById(id);
        validateOwnership(project);
        return mapper.toResponseDTO(project);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getByUser() {
        User currentUser = userProvider.getCurrentUser();
        return repo.findByUserId(currentUser.getId()).stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProjectResponseDTO update(UUID id, ProjectUpdateDTO dto) {
        Project project = repo.findById(id);
        validateOwnership(project);

        if (dto.name() != null) {
            project.setName(dto.name());
        }
        if (dto.description() != null) {
            project.setDescription(dto.description());
        }

        Project updated = repo.save(project);
        return mapper.toResponseDTO(updated);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Project project = repo.findById(id);
        validateOwnership(project);
        repo.delete(id);
    }

    private void validateOwnership(Project project) {
        ownershipValidator.validateProjectOwnership(project);
    }
}