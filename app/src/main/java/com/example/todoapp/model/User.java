package com.example.todoapp.model;

public class User {
    private Long id;
    private String username;
    private String password;

    // 默认构造函数
    public User() {
    }

    // 带参数的构造函数
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}