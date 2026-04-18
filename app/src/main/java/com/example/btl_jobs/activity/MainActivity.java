package com.example.btl_jobs.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.btl_jobs.R;
import com.example.btl_jobs.fragment.ApplicationFragment;
import com.example.btl_jobs.fragment.HomeFragment;
import com.example.btl_jobs.fragment.SavedFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * MainActivity - Màn hình chính với Bottom Navigation
 * Quản lý các Fragment: Home, Saved, Application
 */
public class MainActivity extends AppCompatActivity {
    
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Ánh xạ views
        bottomNavigation = findViewById(R.id.bottomNavigation);
        
        // Load fragment mặc định (HomeFragment)
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }
        
        // Xử lý sự kiện Bottom Navigation
        setupBottomNavigation();
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
}
