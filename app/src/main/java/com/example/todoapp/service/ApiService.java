package com.example.todoapp.service;

import com.example.todoapp.model.Todo;
import com.example.todoapp.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {
    @POST("api/users/register")
    Call<User> register(@Body User user);

    @POST("api/users/login")
    Call<User> login(@Body User user);

    @GET("api/todos/{userId}")
    Call<List<Todo>> getTodosByUserId(@Path("userId") Long userId);

    @POST("api/todos")
    Call<Todo> addTodo(@Body Todo todo);

    @GET("api/todos/detail/{id}")
    Call<Todo> getTodoById(@Path("id") Long id);

    @PUT("/api/todos/{id}")
    Call<Todo> updateTodo(@Path("id") Long id, @Body Todo todo);
}
