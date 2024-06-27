package com.example.todoapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todoapp.R;
import com.example.todoapp.model.Todo;
import com.example.todoapp.service.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TodoDetailActivity extends AppCompatActivity {
    private TextView titleTextView;
    private TextView descriptionTextView;
    private Button editButton;
    private ApiService apiService;
    private Long todoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);

        titleTextView = findViewById(R.id.todo_title);
        descriptionTextView = findViewById(R.id.todo_description);
        editButton = findViewById(R.id.edit_button);

        todoId = getIntent().getLongExtra("todoId", -1);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.123.155:8080/TodoAppApi_war/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        fetchTodoDetails(todoId);

        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(TodoDetailActivity.this, EditTodoActivity.class);
            intent.putExtra("todoId", todoId);
            startActivity(intent);
        });
    }

    private void fetchTodoDetails(Long todoId) {
        apiService.getTodoById(todoId).enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                if (response.isSuccessful()) {
                    Todo todo = response.body();
                    if (todo != null) {
                        titleTextView.setText(todo.getTitle());
                        descriptionTextView.setText(todo.getDescription());
                    }
                }
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                // Handle failure
            }
        });
    }
}
