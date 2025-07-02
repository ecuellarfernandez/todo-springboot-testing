package com.todoapp.project.adapter.out;

import com.todoapp.project.application.mapper.ProjectMapper;
import com.todoapp.project.domain.Project;
import com.todoapp.project.port.out.ProjectRepository;
import com.todoapp.user.adapter.out.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.NoSuchElementException;

@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

    private final ProjectJpaRepository jpaRepository;
    private final ProjectMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    public ProjectRepositoryImpl(ProjectJpaRepository jpaRepository,
                               ProjectMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Project save(Project project) {
        ProjectEntity entity;
        if (project.getId() != null && jpaRepository.existsById(project.getId())) {
            entity = jpaRepository.findById(project.getId()).orElseThrow();
            entity.setName(project.getName());
        } else {
            entity = mapper.domainToEntity(project);
            entity.setOwner(entityManager.getReference(UserEntity.class, project.getUserId()));
        }
        ProjectEntity savedEntity = jpaRepository.save(entity);
        return mapper.entityToDomain(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Project findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::entityToDomain)
                .orElseThrow(() -> new NoSuchElementException("Proyecto no encontrado con id: " + id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Project> findByUserId(UUID userId) {
        List<ProjectEntity> entities = jpaRepository.findByOwnerId(userId);
        return mapper.entitiesToDomains(entities);
    }

    @Override
    public void delete(UUID id) {
    if (!jpaRepository.existsById(id)) {
            throw new NoSuchElementException("No se encontró el proyecto con id: " + id);
    }
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaRepository.existsById(id);
    }
}