package com.example.btl_jobs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.example.btl_jobs.R;
import com.example.btl_jobs.fragment.ApplicationFragment;
import com.example.btl_jobs.fragment.HomeFragment;
import com.example.btl_jobs.fragment.SavedFragment;
import com.example.btl_jobs.utils.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * MainActivity - Màn hình chính với Bottom Navigation
 * Quản lý các Fragment: Home, Saved, Application
 */
public class MainActivity extends AppCompatActivity {
    
    private BottomNavigationView bottomNavigation;
    private SessionManager sessionManager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Kiểm tra đăng nhập
        sessionManager = new SessionManager(this);
        if (!sessionManager.isLoggedIn()) {
            navigateToLogin();
            return;
        }
        
        setContentView(R.layout.activity_main);
        
        // Setup Toolbar
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Job Finder");
            }
        }
        
        // Ánh xạ views
        bottomNavigation = findViewById(R.id.bottomNavigation);
        
        // Load fragment mặc định (HomeFragment)
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }
        
        // Xử lý sự kiện Bottom Navigation
        setupBottomNavigation();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        
        if (id == R.id.action_logout) {
            handleLogout();
            return true;
        } else if (id == R.id.action_profile) {
            // TODO: Mở màn hình profile
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    /**
     * Thiết lập Bottom Navigation
     */
    private void setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int itemId = item.getItemId();
            
            if (itemId == R.id.navigation_home) {
                fragment = new HomeFragment();
            } else if (itemId == R.id.navigation_saved) {
                fragment = new SavedFragment();
            } else if (itemId == R.id.navigation_application) {
                fragment = new ApplicationFragment();
            }
            
            return loadFragment(fragment);
        });
    }
    
    /**
     * Load fragment vào container
     */
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.navHostFragment, fragment)
                .commit();
            return true;
        }
        return false;
    }
    
    /**
     * Xử lý đăng xuất
     */
    private void handleLogout() {
        sessionManager.logout();
        navigateToLogin();
    }
    
    /**
     * Chuyển đến màn hình đăng nhập
     */
    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
