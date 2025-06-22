package com.todoapp.todolist.domain;

import java.util.UUID;

public class TodoList {
    private UUID id;
    private String name;
    private UUID projectId;

    public TodoList() {}

    public TodoList(UUID id, String name, UUID projectId) {
        this.id = id;
        this.name = name;
        this.projectId = projectId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }
}
