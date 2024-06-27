package com.example.todoapp.model;

public class Todo {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private Boolean completed;

    // 默认构造函数
    public Todo() {
    }

    // 带参数的构造函数
    public Todo(String title, String description, Long userId) {
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.completed = false;
    }

    // Getter 和 Setter 方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
