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
import com.example.btl_jobs.fragment.AdminJobsFragment;
import com.example.btl_jobs.fragment.AdminUsersFragment;
import com.example.btl_jobs.model.User;
import com.example.btl_jobs.utils.SessionManager;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;

/**
 * AdminDashboardActivity - Dashboard cho Admin
 */
public class AdminDashboardActivity extends AppCompatActivity {
    
    private TextView tvUserCount, tvJobCount, tvRecruiterCount;
    private TabLayout tabLayout;
    
    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Kiểm tra đăng nhập và role
        sessionManager = new SessionManager(this);
        if (!sessionManager.isLoggedIn() || !sessionManager.isAdmin()) {
            navigateToLogin();
            return;
        }
        
        setContentView(R.layout.activity_admin_dashboard);
        
        // Khởi tạo
        dbHelper = new DatabaseHelper(this);
        
        // Setup toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        // Ánh xạ views
        initViews();
        
        // Load stats
        loadStats();
        
        // Setup tabs
        setupTabs();
        
        // Load fragment mặc định
        if (savedInstanceState == null) {
            loadFragment(new AdminUsersFragment());
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
        tvUserCount = findViewById(R.id.tvUserCount);
        tvJobCount = findViewById(R.id.tvJobCount);
        tvRecruiterCount = findViewById(R.id.tvRecruiterCount);
        tabLayout = findViewById(R.id.tabLayout);
        
        // Null check
        if (tvUserCount == null || tvJobCount == null || tvRecruiterCount == null || tabLayout == null) {
            android.util.Log.e("AdminDashboard", "Views not found in layout!");
        }
    }
    
    private void setupTabs() {
        tabLayout.addTab(tabLayout.newTab().setText("Users"));
        tabLayout.addTab(tabLayout.newTab().setText("Jobs"));
        
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment;
                if (tab.getPosition() == 0) {
                    fragment = new AdminUsersFragment();
                } else {
                    fragment = new AdminJobsFragment();
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
        try {
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
        } catch (Exception e) {
            android.util.Log.e("AdminDashboard", "Error loading fragment: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void loadStats() {
        try {
            int totalUsers = dbHelper.getAllUsers().size();
            int totalJobs = dbHelper.getAllJobs().size();
            
            // Đếm số recruiters
            int recruiterCount = 0;
            for (User user : dbHelper.getAllUsers()) {
                if (User.ROLE_RECRUITER.equals(user.getRole())) {
                    recruiterCount++;
                }
            }
            
            if (tvUserCount != null) tvUserCount.setText(String.valueOf(totalUsers));
            if (tvJobCount != null) tvJobCount.setText(String.valueOf(totalJobs));
            if (tvRecruiterCount != null) tvRecruiterCount.setText(String.valueOf(recruiterCount));
        } catch (Exception e) {
            android.util.Log.e("AdminDashboard", "Error loading stats: " + e.getMessage());
            e.printStackTrace();
        }
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
