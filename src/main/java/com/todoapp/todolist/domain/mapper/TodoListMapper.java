package com.todoapp.todolist.domain.mapper;

import com.todoapp.todolist.adapter.out.TodoListEntity;
import com.todoapp.todolist.domain.TodoList;
import com.todoapp.todolist.dto.TodoListResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TodoList.class})
public interface TodoListMapper {

    @Mapping(target = "projectId", source = "projectId")
    TodoListResponseDTO toTodoListResponseDTO(TodoList todoList);

    @Mapping(target = "projectId", source = "project.id")
    TodoList entityToDomain(TodoListEntity todoListEntity);

    TodoListEntity domainToEntity(TodoList todoList);

    List<TodoList> entitiesToDomains(List<TodoListEntity> todoListEntities);
}