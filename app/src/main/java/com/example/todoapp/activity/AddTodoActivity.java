package com.example.todoapp.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todoapp.R;
import com.example.todoapp.model.Todo;
import com.example.todoapp.service.ApiService;
import com.example.todoapp.service.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTodoActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText descriptionEditText;
    private Button addButton;
    private ApiService apiService;
    private Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        titleEditText = findViewById(R.id.todo_title);
        descriptionEditText = findViewById(R.id.todo_description);
        addButton = findViewById(R.id.add_button);

        userId = getIntent().getLongExtra("userId", -1);


        apiService = RetrofitClient.getApiService();

        addButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString();
            String description = descriptionEditText.getText().toString();

            Todo todo = new Todo();
            todo.setUserId(userId);
            todo.setTitle(title);
            todo.setDescription(description);
            todo.setCompleted(false);  // 设置 completed 字段为 false

            addTodoItem(todo);
        });
    }

    private void addTodoItem(Todo todo) {
        apiService.addTodo(todo).enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                if (response.isSuccessful()) {
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(AddTodoActivity.this, "Failed to add todo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                Toast.makeText(AddTodoActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
