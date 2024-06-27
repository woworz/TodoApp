package com.example.todoapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.todoapp.R;
import com.example.todoapp.activity.TodoDetailActivity;
import com.example.todoapp.model.Todo;
import com.example.todoapp.service.ApiService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {
    private List<Todo> todos;
    private Context context;
    private ApiService apiService;

    public TodoAdapter(List<Todo> todos, Context context) {
        this.todos = todos;
        this.context = context;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.123.155:8080/TodoAppApi_war/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo todo = todos.get(position);
        holder.titleTextView.setText(todo.getTitle());
        holder.completedCheckBox.setChecked(todo.getCompleted() != null ? todo.getCompleted() : false);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TodoDetailActivity.class);
            intent.putExtra("todoId", todo.getId());
            context.startActivity(intent);
        });

        holder.completedCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            todo.setCompleted(isChecked);
            updateTodoCompletedStatus(todo);
        });
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
        notifyDataSetChanged();
    }

    private void updateTodoCompletedStatus(Todo todo) {
        apiService.updateTodo(todo.getId(), todo).enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                if (!response.isSuccessful()) {
                    // Handle failure
                }
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                // Handle failure
            }
        });
    }

    class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        CheckBox completedCheckBox;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.todo_title);
            completedCheckBox = itemView.findViewById(R.id.todo_completed);
        }
    }
}
