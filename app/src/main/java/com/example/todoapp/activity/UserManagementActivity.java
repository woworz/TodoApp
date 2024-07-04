package com.example.todoapp.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todoapp.R;
import com.example.todoapp.service.ApiService;
import com.example.todoapp.model.User;
import com.example.todoapp.service.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserManagementActivity extends AppCompatActivity {
    private EditText currentPasswordEditText;
    private EditText newPasswordEditText;
    private EditText confirmNewPasswordEditText;
    private Button changePasswordButton;
    private ApiService apiService;
    private Long userId;

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String PREF_USER_ID = "userId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);

        newPasswordEditText = findViewById(R.id.new_password);
        confirmNewPasswordEditText = findViewById(R.id.confirm_new_password);
        changePasswordButton = findViewById(R.id.change_password_button);

        apiService = RetrofitClient.getApiService();

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        userId = sharedPreferences.getLong(PREF_USER_ID, -1);

        changePasswordButton.setOnClickListener(v -> {
            String newPassword = newPasswordEditText.getText().toString();
            String confirmNewPassword = confirmNewPasswordEditText.getText().toString();

            if (!newPassword.equals(confirmNewPassword)) {
                Toast.makeText(UserManagementActivity.this, "New passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            changePassword(userId, newPassword);
        });
    }

    private void changePassword(Long userId, String newPassword) {
        User user = new User();
        user.setId(userId);
        user.setPassword(newPassword); // 假设服务器端会验证当前密码并更新为新密码

        apiService.changePassword(userId, user).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UserManagementActivity.this, "修改密码成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UserManagementActivity.this, "Failed to change password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UserManagementActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
