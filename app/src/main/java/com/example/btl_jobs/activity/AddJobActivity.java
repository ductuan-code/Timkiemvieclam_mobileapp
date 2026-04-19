package com.example.btl_jobs.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_jobs.R;
import com.example.btl_jobs.database.DatabaseHelper;
import com.example.btl_jobs.model.Job;
import com.example.btl_jobs.utils.SessionManager;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * AddJobActivity - Màn hình đăng tin tuyển dụng (Recruiter)
 */
public class AddJobActivity extends AppCompatActivity {
    
    private TextInputEditText etJobTitle, etCompany, etSalary, etLocation, etJobType;
    private TextInputEditText etDescription, etRequirements, etBenefits;
    private MaterialButton btnSubmit;
    
    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;
    private int recruiterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);
        
        // Khởi tạo
        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);
        recruiterId = sessionManager.getUserId();
        
        // Setup toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());
        
        // Ánh xạ views
        initViews();
        
        // Setup listeners
        setupListeners();
    }
    
    private void initViews() {
        etJobTitle = findViewById(R.id.etJobTitle);
        etCompany = findViewById(R.id.etCompany);
        etSalary = findViewById(R.id.etSalary);
        etLocation = findViewById(R.id.etLocation);
        etJobType = findViewById(R.id.etJobType);
        etDescription = findViewById(R.id.etDescription);
        etRequirements = findViewById(R.id.etRequirements);
        etBenefits = findViewById(R.id.etBenefits);
        btnSubmit = findViewById(R.id.btnSubmit);
    }
    
    private void setupListeners() {
        btnSubmit.setOnClickListener(v -> handleSubmit());
    }
    
    private void handleSubmit() {
        String title = etJobTitle.getText().toString().trim();
        String company = etCompany.getText().toString().trim();
        String salary = etSalary.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String type = etJobType.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String requirements = etRequirements.getText().toString().trim();
        String benefits = etBenefits.getText().toString().trim();
        
        // Validation
        if (title.isEmpty()) {
            etJobTitle.setError("Vui lòng nhập tiêu đề");
            etJobTitle.requestFocus();
            return;
        }
        
        if (company.isEmpty()) {
            etCompany.setError("Vui lòng nhập tên công ty");
            etCompany.requestFocus();
            return;
        }
        
        if (salary.isEmpty()) {
            etSalary.setError("Vui lòng nhập mức lương");
            etSalary.requestFocus();
            return;
        }
        
        if (location.isEmpty()) {
            etLocation.setError("Vui lòng nhập địa điểm");
            etLocation.requestFocus();
            return;
        }
        
        if (type.isEmpty()) {
            etJobType.setError("Vui lòng nhập loại hình");
            etJobType.requestFocus();
            return;
        }
        
        if (description.isEmpty()) {
            etDescription.setError("Vui lòng nhập mô tả");
            etDescription.requestFocus();
            return;
        }
        
        if (requirements.isEmpty()) {
            etRequirements.setError("Vui lòng nhập yêu cầu");
            etRequirements.requestFocus();
            return;
        }
        
        if (benefits.isEmpty()) {
            etBenefits.setError("Vui lòng nhập quyền lợi");
            etBenefits.requestFocus();
            return;
        }
        
        // Tạo job mới
        Job job = new Job(title, description, salary, location, type, recruiterId, 
                         company, requirements, benefits);
        
        // Lưu vào database
        long result = dbHelper.addJob(job);
        
        if (result != -1) {
            Toast.makeText(this, "Đăng tin tuyển dụng thành công!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Lỗi khi đăng tin!", Toast.LENGTH_SHORT).show();
        }
    }
}
