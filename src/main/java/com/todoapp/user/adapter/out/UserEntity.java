package com.todoapp.user.adapter.out;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "username")
    public String username;

    @Column(name = "name")
    public String name;

    @Column(name = "email")
    public String email;

    @Column(name = "password")
    public String password;
}

