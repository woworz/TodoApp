package com.example.todoapp.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todoapp.R;
import com.example.todoapp.model.Todo;
import com.example.todoapp.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditTodoActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText descriptionEditText;
    private Button saveButton;
    private ApiService apiService;
    private Long todoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);

        titleEditText = findViewById(R.id.edit_todo_title);
        descriptionEditText = findViewById(R.id.edit_todo_description);
        saveButton = findViewById(R.id.save_button);

        todoId = getIntent().getLongExtra("todoId", -1);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.123.155:8080/TodoAppApi_war/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        fetchTodoDetails(todoId);

        saveButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString();
            String description = descriptionEditText.getText().toString();

            Todo todo = new Todo();
            todo.setId(todoId);
            todo.setTitle(title);
            todo.setDescription(description);

            updateTodoItem(todo);
        });
    }

    private void fetchTodoDetails(Long todoId) {
        apiService.getTodoById(todoId).enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                if (response.isSuccessful()) {
                    Todo todo = response.body();
                    if (todo != null) {
                        titleEditText.setText(todo.getTitle());
                        descriptionEditText.setText(todo.getDescription());
                    }
                }
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                // Handle failure
            }
        });
    }

    private void updateTodoItem(Todo todo) {
        apiService.updateTodo(todo.getId(), todo).enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditTodoActivity.this, "Todo updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditTodoActivity.this, "Failed to update todo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                Toast.makeText(EditTodoActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
