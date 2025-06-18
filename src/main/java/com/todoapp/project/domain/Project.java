package com.todoapp.project.domain;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.UUID;

public class Project {
    private UUID id;
    private String name;
    private String description;
    private UUID userId;
    private LocalDateTime createdAt;

    @ConstructorProperties({"id", "name", "description", "userId", "createdAt"})
    public Project(UUID id, String name, String description, UUID userId, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    // Getters y setters
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}