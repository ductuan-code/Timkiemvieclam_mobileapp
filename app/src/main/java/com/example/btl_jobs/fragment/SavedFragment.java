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
import com.example.btl_jobs.adapter.JobAdapter;
import com.example.btl_jobs.database.DatabaseHelper;
import com.example.btl_jobs.model.Job;
import com.example.btl_jobs.utils.SessionManager;
import java.util.List;

/**
 * SavedFragment - Màn hình hiển thị các công việc đã lưu
 */
public class SavedFragment extends Fragment implements JobAdapter.OnJobActionListener {
    
    private RecyclerView rvSavedJobs;
    private LinearLayout layoutEmpty;
    private JobAdapter jobAdapter;
    private List<Job> savedJobList;
    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;
    private int currentUserId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, 
                            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved, container, false);
        
        // Khởi tạo
        dbHelper = new DatabaseHelper(getContext());
        sessionManager = new SessionManager(getContext());
        currentUserId = sessionManager.getUserId();
        
        // Ánh xạ views
        initViews(view);
        
        // Load dữ liệu
        loadSavedJobs();
        
        // Setup RecyclerView
        setupRecyclerView();
        
        return view;
    }
    
    /**
     * Ánh xạ các view
     */
    private void initViews(View view) {
        rvSavedJobs = view.findViewById(R.id.rvSavedJobs);
        layoutEmpty = view.findViewById(R.id.layoutEmpty);
    }
    
    /**
     * Load danh sách job đã lưu từ SQLite
     */
    private void loadSavedJobs() {
        savedJobList = dbHelper.getSavedJobs(currentUserId);
        // Đánh dấu tất cả là saved
        for (Job job : savedJobList) {
            job.setSaved(true);
        }
    }
    
    /**
     * Setup RecyclerView
     */
    private void setupRecyclerView() {
        jobAdapter = new JobAdapter(getContext(), savedJobList, this);
        rvSavedJobs.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSavedJobs.setAdapter(jobAdapter);
        
        updateEmptyState();
    }
    
    /**
     * Cập nhật empty state
     */
    private void updateEmptyState() {
        if (savedJobList.isEmpty()) {
            layoutEmpty.setVisibility(View.VISIBLE);
            rvSavedJobs.setVisibility(View.GONE);
        } else {
            layoutEmpty.setVisibility(View.GONE);
            rvSavedJobs.setVisibility(View.VISIBLE);
        }
    }
    
    /**
     * Xử lý khi unsave job
     */
    @Override
    public void onSaveClick(Job job, int position) {
        // Xóa khỏi database
        dbHelper.unsaveJob(currentUserId, job.getId());
        
        // Xóa khỏi danh sách
        savedJobList.remove(position);
        jobAdapter.notifyItemRemoved(position);
        updateEmptyState();
    }
    
    @Override
    public void onResume() {
        super.onResume();
        // Reload dữ liệu khi quay lại fragment
        loadSavedJobs();
        if (jobAdapter != null) {
            jobAdapter = new JobAdapter(getContext(), savedJobList, this);
            rvSavedJobs.setAdapter(jobAdapter);
            updateEmptyState();
        }
    }
}
