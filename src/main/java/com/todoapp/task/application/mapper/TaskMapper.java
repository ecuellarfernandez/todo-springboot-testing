package com.todoapp.task.application.mapper;

import com.todoapp.task.adapter.out.TaskEntity;
import com.todoapp.task.domain.Task;
import com.todoapp.task.dto.TaskResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Task entityToDomain(TaskEntity taskEntity);
    TaskEntity domainToEntity(Task task);
    TaskResponseDTO toResponseDTO(Task task);
}