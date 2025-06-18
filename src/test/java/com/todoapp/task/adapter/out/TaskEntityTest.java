package com.todoapp.task.adapter.out;

import com.todoapp.todolist.adapter.out.TodoListEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskEntityTest {
    @Test
    void testTaskEntityGettersAndSetters() {
        TaskEntity task = new TaskEntity();
        UUID id = UUID.randomUUID();
        String title = "Título de prueba";
        String description = "Descripción de prueba";
        String status = "PENDING";
        LocalDate dueDate = LocalDate.of(2025, 1, 1);
        TodoListEntity todoList = new TodoListEntity();

        task.setId(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        task.setDueDate(dueDate);
        task.setTodoList(todoList);

        assertEquals(id, task.getId());
        assertEquals(title, task.getTitle());
        assertEquals(description, task.getDescription());
        assertEquals(status, task.getStatus());
        assertEquals(dueDate, task.getDueDate());
        assertEquals(todoList, task.getTodoList());
    }

    @Test
    void testTaskEntityDefaultValues() {
        TaskEntity task = new TaskEntity();
        assertNull(task.getId());
        assertNull(task.getTitle());
        assertNull(task.getDescription());
        assertNull(task.getStatus());
        assertNull(task.getDueDate());
        assertNull(task.getTodoList());
    }
} 