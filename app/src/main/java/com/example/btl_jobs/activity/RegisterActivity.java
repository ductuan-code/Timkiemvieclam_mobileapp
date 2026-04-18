package com.example.btl_jobs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_jobs.R;
import com.example.btl_jobs.database.DatabaseHelper;
import com.example.btl_jobs.model.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * RegisterActivity - Màn hình đăng ký
 */
public class RegisterActivity extends AppCompatActivity {
    
    private ImageButton btnBack;
    private TextInputEditText etFullName, etEmail, etPassword, etConfirmPassword;
    private RadioGroup rgRole;
    private RadioButton rbUser, rbRecruiter;
    private MaterialButton btnRegister;
    private TextView tvLogin;
    
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        // Khởi tạo
        dbHelper = new DatabaseHelper(this);
        
        // Ánh xạ views
        initViews();
        
        // Setup listeners
        setupListeners();
    }
    
    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        rgRole = findViewById(R.id.rgRole);
        rbUser = findViewById(R.id.rbUser);
        rbRecruiter = findViewById(R.id.rbRecruiter);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);
    }
    
    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());
        btnRegister.setOnClickListener(v -> handleRegister());
        tvLogin.setOnClickListener(v -> finish());
    }
    
    private void handleRegister() {
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        
        // Validation
        if (fullName.isEmpty()) {
            etFullName.setError("Vui lòng nhập họ tên");
            etFullName.requestFocus();
            return;
        }
        
        if (email.isEmpty()) {
            etEmail.setError("Vui lòng nhập email");
            etEmail.requestFocus();
            return;
        }
        
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Email không hợp lệ");
            etEmail.requestFocus();
            return;
        }
        
        if (password.isEmpty()) {
            etPassword.setError("Vui lòng nhập mật khẩu");
            etPassword.requestFocus();
            return;
        }
        
        if (password.length() < 6) {
            etPassword.setError("Mật khẩu phải có ít nhất 6 ký tự");
            etPassword.requestFocus();
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Mật khẩu không khớp");
            etConfirmPassword.requestFocus();
            return;
        }
        
        // Kiểm tra email đã tồn tại
        if (dbHelper.isEmailExists(email)) {
            etEmail.setError("Email đã được sử dụng");
            etEmail.requestFocus();
            return;
        }
        
        // Lấy role
        String role = rbUser.isChecked() ? User.ROLE_USER : User.ROLE_RECRUITER;
        
        // Tạo user mới
        User user = new User(fullName, email, password, role);
        long result = dbHelper.registerUser(user);
        
        if (result != -1) {
            Toast.makeText(this, "Đăng ký thành công! Vui lòng đăng nhập", 
                    Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Đăng ký thất bại! Vui lòng thử lại", 
                    Toast.LENGTH_SHORT).show();
        }
    }
}
