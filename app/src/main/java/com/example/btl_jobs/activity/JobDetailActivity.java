package com.example.btl_jobs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_jobs.R;
import com.example.btl_jobs.model.Job;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

/**
 * JobDetailActivity - Màn hình chi tiết công việc
 */
public class JobDetailActivity extends AppCompatActivity {
    
    public static final String EXTRA_JOB = "extra_job";
    
    private Job job;
    private ImageView ivCompanyLogo;
    private TextView tvJobTitle, tvCompany, tvSalary, tvLocation, tvType;
    private TextView tvDescription, tvRequirements, tvBenefits;
    private MaterialButton btnApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        
        // Nhận dữ liệu job từ Intent
        job = (Job) getIntent().getSerializableExtra(EXTRA_JOB);
        
        if (job == null) {
            finish();
            return;
        }
        
        // Ánh xạ views
        initViews();
        
        // Hiển thị dữ liệu
        displayJobData();
        
        // Xử lý sự kiện
        setupListeners();
    }
    
    /**
     * Ánh xạ các view
     */
    private void initViews() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        ivCompanyLogo = findViewById(R.id.ivCompanyLogo);
        tvJobTitle = findViewById(R.id.tvJobTitle);
        tvCompany = findViewById(R.id.tvCompany);
        tvSalary = findViewById(R.id.tvSalary);
        tvLocation = findViewById(R.id.tvLocation);
        tvType = findViewById(R.id.tvType);
        tvDescription = findViewById(R.id.tvDescription);
        tvRequirements = findViewById(R.id.tvRequirements);
        tvBenefits = findViewById(R.id.tvBenefits);
        btnApply = findViewById(R.id.btnApply);
    }
    
    /**
     * Hiển thị thông tin công việc
     */
    private void displayJobData() {
        tvJobTitle.setText(job.getTitle());
        tvCompany.setText(job.getCompany());
        tvSalary.setText(job.getSalary());
        tvLocation.setText(job.getLocation());
        tvType.setText(job.getType());
        tvDescription.setText(job.getDescription());
        tvRequirements.setText(job.getRequirements());
        tvBenefits.setText(job.getBenefits());
        
        // Load company logo (sử dụng Glide nếu có URL thật)
        // Glide.with(this).load(job.getCompanyLogo()).into(ivCompanyLogo);
    }
    
    /**
     * Thiết lập các listener
     */
    private void setupListeners() {
        // Nút back
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        // Nút Apply
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobDetailActivity.this, ApplyActivity.class);
                intent.putExtra(ApplyActivity.EXTRA_JOB, job);
                startActivity(intent);
            }
        });
    }
}