package com.example.btl_jobs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_jobs.R;
import com.example.btl_jobs.database.DatabaseHelper;
import com.example.btl_jobs.model.User;
import com.example.btl_jobs.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * LoginActivity - Màn hình đăng nhập
 */
public class LoginActivity extends AppCompatActivity {
    
    private TextInputEditText etEmail, etPassword;
    private MaterialButton btnLogin;
    private TextView tvRegister;
    
    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        // Khởi tạo
        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);
        
        // Kiểm tra đã đăng nhập chưa
        if (sessionManager.isLoggedIn()) {
            navigateToMain();
            return;
        }
        
        // Ánh xạ views
        initViews();
        
        // Setup listeners
        setupListeners();
    }
    
    private void initViews() {
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
    }
    
    private void setupListeners() {
        btnLogin.setOnClickListener(v -> handleLogin());
        tvRegister.setOnClickListener(v -> navigateToRegister());
    }
    
    private void handleLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        
        // Validation
        if (email.isEmpty()) {
            etEmail.setError("Vui lòng nhập email");
            etEmail.requestFocus();
            return;
        }
        
        if (password.isEmpty()) {
            etPassword.setError("Vui lòng nhập mật khẩu");
            etPassword.requestFocus();
            return;
        }
        
        // Đăng nhập
        User user = dbHelper.login(email, password);
        
        if (user != null) {
            // Lưu session
            sessionManager.createLoginSession(user);
            
            Toast.makeText(this, "Đăng nhập thành công! Xin chào " + user.getFullName(), 
                    Toast.LENGTH_SHORT).show();
            
            navigateToMain();
        } else {
            Toast.makeText(this, "Email hoặc mật khẩu không đúng!", 
                    Toast.LENGTH_SHORT).show();
        }
    }
    
    private void navigateToRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    
    private void navigateToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
