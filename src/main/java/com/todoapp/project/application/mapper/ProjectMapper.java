package com.todoapp.project.application.mapper;

import com.todoapp.project.adapter.out.ProjectEntity;
import com.todoapp.project.domain.Project;
import com.todoapp.project.dto.ProjectResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(source = "userId", target = "userId")
    ProjectResponseDTO toResponseDTO(Project project);

    @Mapping(target = "owner", ignore = true)
    ProjectEntity domainToEntity(Project project);

    List<Project> entitiesToDomains(List<ProjectEntity> entities);

    @Mapping(target = "userId", source = "owner.id")
    Project entityToDomain(ProjectEntity entity);
}