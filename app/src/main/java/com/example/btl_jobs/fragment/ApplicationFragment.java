package com.example.btl_jobs.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.btl_jobs.R;
import com.example.btl_jobs.adapter.ApplicationAdapter;
import com.example.btl_jobs.database.DatabaseHelper;
import com.example.btl_jobs.model.Application;
import java.util.List;

/**
 * ApplicationFragment - Màn hình hiển thị danh sách đơn ứng tuyển
 */
public class ApplicationFragment extends Fragment {
    
    private RecyclerView rvApplications;
    private LinearLayout layoutEmpty;
    private ApplicationAdapter applicationAdapter;
    private List<Application> applicationList;
    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, 
                            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_application, container, false);
        
        // Khởi tạo database
        dbHelper = new DatabaseHelper(getContext());
        
        // Ánh xạ views
        initViews(view);
        
        // Load dữ liệu
        loadApplications();
        
        // Setup RecyclerView
        setupRecyclerView();
        
        return view;
    }
    
    /**
     * Ánh xạ các view
     */
    private void initViews(View view) {
        rvApplications = view.findViewById(R.id.rvApplications);
        layoutEmpty = view.findViewById(R.id.layoutEmpty);
    }
    
    /**
     * Load danh sách đơn ứng tuyển từ SQLite
     */
    private void loadApplications() {
        applicationList = dbHelper.getAllApplications();
    }
    
    /**
     * Setup RecyclerView
     */
    private void setupRecyclerView() {
        applicationAdapter = new ApplicationAdapter(getContext(), applicationList);
        rvApplications.setLayoutManager(new LinearLayoutManager(getContext()));
        rvApplications.setAdapter(applicationAdapter);
        
        updateEmptyState();
    }
    
    /**
     * Cập nhật empty state
     */
    private void updateEmptyState() {
        if (applicationList.isEmpty()) {
            layoutEmpty.setVisibility(View.VISIBLE);
            rvApplications.setVisibility(View.GONE);
        } else {
            layoutEmpty.setVisibility(View.GONE);
            rvApplications.setVisibility(View.VISIBLE);
        }
    }
    
    @Override
    public void onResume() {
        super.onResume();
        // Reload dữ liệu khi quay lại fragment
        loadApplications();
        if (applicationAdapter != null) {
            applicationAdapter = new ApplicationAdapter(getContext(), applicationList);
            rvApplications.setAdapter(applicationAdapter);
            updateEmptyState();
        }
    }
}
