package com.todoapp.task.application.mapper;

import com.todoapp.task.adapter.out.TaskEntity;
import com.todoapp.task.domain.Task;
import com.todoapp.task.dto.TaskResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(target = "todoListId", source = "todoList.id")
    @Mapping(target = "projectId", source = "todoList.project.id")
    Task entityToDomain(TaskEntity taskEntity);

    TaskEntity domainToEntity(Task task);
    TaskResponseDTO toResponseDTO(Task task);
    List<Task> entitiesToDomains(List<TaskEntity> taskEntities);
}