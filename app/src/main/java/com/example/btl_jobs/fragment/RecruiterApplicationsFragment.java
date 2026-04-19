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
import com.example.btl_jobs.utils.SessionManager;
import java.util.List;

/**
 * RecruiterApplicationsFragment - Hiển thị danh sách ứng viên của Recruiter
 */
public class RecruiterApplicationsFragment extends Fragment {
    
    private RecyclerView rvApplications;
    private LinearLayout layoutEmpty;
    private ApplicationAdapter adapter;
    private List<Application> applicationList;
    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;
    private int recruiterId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, 
                            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recruiter_applications, container, false);
        
        // Khởi tạo
        dbHelper = new DatabaseHelper(getContext());
        sessionManager = new SessionManager(getContext());
        recruiterId = sessionManager.getUserId();
        
        // Ánh xạ views
        rvApplications = view.findViewById(R.id.rvApplications);
        layoutEmpty = view.findViewById(R.id.layoutEmpty);
        
        // Load dữ liệu
        loadApplications();
        
        // Setup RecyclerView
        setupRecyclerView();
        
        return view;
    }
    
    private void loadApplications() {
        applicationList = dbHelper.getApplicationsByRecruiter(recruiterId);
    }
    
    private void setupRecyclerView() {
        adapter = new ApplicationAdapter(getContext(), applicationList);
        rvApplications.setLayoutManager(new LinearLayoutManager(getContext()));
        rvApplications.setAdapter(adapter);
        
        updateEmptyState();
    }
    
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
        loadApplications();
        if (adapter != null) {
            adapter = new ApplicationAdapter(getContext(), applicationList);
            rvApplications.setAdapter(adapter);
            updateEmptyState();
        }
    }
}
