package com.todoapp.task.adapter.out;

import com.todoapp.todolist.adapter.out.TodoListEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private boolean completed;

    private LocalDate dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_list_id", nullable = false)
    private TodoListEntity todoList;

    public TaskEntity() {
    }

    public TaskEntity(TodoListEntity todoList, LocalDate dueDate, boolean completed, String description, String title, UUID id) {
        this.todoList = todoList;
        this.dueDate = dueDate;
        this.completed = completed;
        this.description = description;
        this.title = title;
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public TodoListEntity getTodoList() {
        return todoList;
    }

    public void setTodoList(TodoListEntity todoList) {
        this.todoList = todoList;
    }
}
