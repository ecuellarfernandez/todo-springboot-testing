package com.todoapp.project.adapter.out;

import com.todoapp.todolist.adapter.out.TodoListEntity;
import com.todoapp.user.adapter.out.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "projects")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity owner;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TodoListEntity> todoLists = new ArrayList<>();

    private LocalDateTime createdAt;

    // Constructores
    public ProjectEntity() {
    }

    public ProjectEntity(UUID id, String name, String description, UserEntity owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
    }

    // Getters y setters
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

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public List<TodoListEntity> getTodoLists() {
        return todoLists;
    }

    public void setTodoLists(List<TodoListEntity> todoLists) {
        this.todoLists = todoLists;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}