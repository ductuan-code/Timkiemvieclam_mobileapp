package com.example.btl_jobs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.btl_jobs.R;
import com.example.btl_jobs.database.DatabaseHelper;
import com.example.btl_jobs.fragment.RecruiterApplicationsFragment;
import com.example.btl_jobs.fragment.RecruiterJobsFragment;
import com.example.btl_jobs.utils.SessionManager;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

/**
 * RecruiterDashboardActivity - Dashboard cho Recruiter
 */
public class RecruiterDashboardActivity extends AppCompatActivity {
    
    private TextView tvJobCount, tvApplicationCount;
    private TabLayout tabLayout;
    private FloatingActionButton fabAddJob;
    
    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;
    private int recruiterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Kiểm tra đăng nhập và role
        sessionManager = new SessionManager(this);
        if (!sessionManager.isLoggedIn() || !sessionManager.isRecruiter()) {
            navigateToLogin();
            return;
        }
        
        setContentView(R.layout.activity_recruiter_dashboard);
        
        // Khởi tạo
        dbHelper = new DatabaseHelper(this);
        recruiterId = sessionManager.getUserId();
        
        // Setup toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        // Ánh xạ views
        initViews();
        
        // Load stats
        loadStats();
        
        // Setup tabs
        setupTabs();
        
        // Setup listeners
        setupListeners();
        
        // Load fragment mặc định
        if (savedInstanceState == null) {
            loadFragment(new RecruiterJobsFragment());
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            handleLogout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    private void initViews() {
        tvJobCount = findViewById(R.id.tvJobCount);
        tvApplicationCount = findViewById(R.id.tvApplicationCount);
        tabLayout = findViewById(R.id.tabLayout);
        fabAddJob = findViewById(R.id.fabAddJob);
    }
    
    private void setupTabs() {
        tabLayout.addTab(tabLayout.newTab().setText("Tin tuyển dụng"));
        tabLayout.addTab(tabLayout.newTab().setText("Ứng viên"));
        
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment;
                if (tab.getPosition() == 0) {
                    fragment = new RecruiterJobsFragment();
                    fabAddJob.show();
                } else {
                    fragment = new RecruiterApplicationsFragment();
                    fabAddJob.hide();
                }
                loadFragment(fragment);
            }
            
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }
    
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.viewPager, fragment)
            .commit();
    }
    
    private void loadStats() {
        int jobCount = dbHelper.getJobsByRecruiter(recruiterId).size();
        int applicationCount = dbHelper.getApplicationsByRecruiter(recruiterId).size();
        
        tvJobCount.setText(String.valueOf(jobCount));
        tvApplicationCount.setText(String.valueOf(applicationCount));
    }
    
    private void setupListeners() {
        fabAddJob.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddJobActivity.class);
            startActivity(intent);
        });
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        loadStats();
    }
    
    private void handleLogout() {
        sessionManager.logout();
        navigateToLogin();
    }
    
    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
