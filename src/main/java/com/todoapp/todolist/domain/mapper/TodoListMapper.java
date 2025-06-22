package com.todoapp.todolist.domain.mapper;

import com.todoapp.task.application.mapper.TaskMapper;
import com.todoapp.todolist.adapter.out.TodoListEntity;
import com.todoapp.todolist.domain.TodoList;
import com.todoapp.todolist.dto.TodoListResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = TaskMapper.class)
public interface TodoListMapper {

    TodoListResponseDTO toTodoListResponseDTO(TodoList todoList);

    TodoList entityToDomain(TodoListEntity todoListEntity);

    TodoListEntity domainToEntity(TodoList todoList);

    List<TodoListResponseDTO> toTodoListResponseDTOs(List<TodoList> todoLists);
    List<TodoList> entitiesToDomains(List<TodoListEntity> todoListEntities);
    List<TodoListEntity> domainsToEntities(List<TodoList> todoLists);
}