package com.example.btl_jobs.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.btl_jobs.R;
import com.example.btl_jobs.database.DatabaseHelper;
import com.example.btl_jobs.model.Application;
import com.example.btl_jobs.model.Job;
import com.example.btl_jobs.utils.SessionManager;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * ApplyActivity - Màn hình ứng tuyển công việc
 */
public class ApplyActivity extends AppCompatActivity {
    
    public static final String EXTRA_JOB = "extra_job";
    
    private Job job;
    private TextView tvJobTitle, tvCompany, tvFileName;
    private TextInputEditText etCoverLetter;
    private MaterialButton btnUploadCV, btnSubmit;
    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;
    private int currentUserId;
    private String cvFileName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        
        // Nhận dữ liệu job
        if (getIntent() != null) {
            job = (Job) getIntent().getSerializableExtra(EXTRA_JOB);
        }
        
        if (job == null) {
            finish();
            return;
        }
        
        // Khởi tạo
        dbHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);
        currentUserId = sessionManager.getUserId();
        
        // Kiểm tra đã ứng tuyển chưa
        if (dbHelper.hasApplied(currentUserId, job.getId())) {
            Toast.makeText(this, "Bạn đã ứng tuyển công việc này rồi!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        
        // Ánh xạ views
        initViews();
        
        // Hiển thị thông tin job
        displayJobInfo();
        
        // Xử lý sự kiện
        setupListeners();
    }
    
    /**
     * Ánh xạ các view
     */
    private void initViews() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        
        tvJobTitle = findViewById(R.id.tvJobTitle);
        tvCompany = findViewById(R.id.tvCompany);
        tvFileName = findViewById(R.id.tvFileName);
        etCoverLetter = findViewById(R.id.etCoverLetter);
        btnUploadCV = findViewById(R.id.btnUploadCV);
        btnSubmit = findViewById(R.id.btnSubmit);
    }
    
    /**
     * Hiển thị thông tin job
     */
    private void displayJobInfo() {
        if (tvJobTitle != null) tvJobTitle.setText(job.getTitle());
        if (tvCompany != null) tvCompany.setText(job.getCompany());
    }
    
    /**
     * Thiết lập các listener
     */
    private void setupListeners() {
        // Nút back
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        
        // Nút upload CV (giả lập)
        if (btnUploadCV != null) {
            btnUploadCV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cvFileName = "CV_" + System.currentTimeMillis() + ".pdf";
                    if (tvFileName != null) tvFileName.setText(cvFileName);
                    Toast.makeText(ApplyActivity.this, "Đã chọn file CV", Toast.LENGTH_SHORT).show();
                }
            });
        }
        
        // Nút submit
        if (btnSubmit != null) {
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submitApplication();
                }
            });
        }
    }
    
    /**
     * Gửi đơn ứng tuyển
     */
    private void submitApplication() {
        if (etCoverLetter == null) return;

        String coverLetter = etCoverLetter.getText().toString().trim();
        
        // Validate
        if (TextUtils.isEmpty(coverLetter)) {
            etCoverLetter.setError("Vui lòng nhập thư giới thiệu");
            return;
        }
        
        if (TextUtils.isEmpty(cvFileName)) {
            Toast.makeText(this, "Vui lòng chọn file CV", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Hiển thị loading
        btnSubmit.setEnabled(false);
        btnSubmit.setText("Đang gửi...");
        
        // Tạo application
        Application application = new Application(
            currentUserId,
            job.getId(),
            job.getTitle(),
            job.getCompany(),
            coverLetter,
            Application.STATUS_PENDING
        );
        
        // Lưu vào SQLite
        long result = dbHelper.applyJob(application);
        
        if (result != -1) {
            Toast.makeText(this, "Gửi đơn ứng tuyển thành công!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            btnSubmit.setEnabled(true);
            btnSubmit.setText("Gửi Đơn Ứng Tuyển");
            Toast.makeText(this, "Lỗi khi gửi đơn", Toast.LENGTH_SHORT).show();
        }
    }
}
