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
import com.example.btl_jobs.adapter.AdminJobAdapter;
import com.example.btl_jobs.database.DatabaseHelper;
import com.example.btl_jobs.model.Job;
import java.util.List;

/**
 * AdminJobsFragment - Hiển thị tất cả jobs
 */
public class AdminJobsFragment extends Fragment implements AdminJobAdapter.OnJobDeletedListener {
    
    private RecyclerView rvJobs;
    private LinearLayout layoutEmpty;
    private AdminJobAdapter adapter;
    private List<Job> jobList;
    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, 
                            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_jobs, container, false);
        
        // Khởi tạo
        dbHelper = new DatabaseHelper(getContext());
        
        // Ánh xạ views
        rvJobs = view.findViewById(R.id.rvJobs);
        layoutEmpty = view.findViewById(R.id.layoutEmpty);
        
        // Load dữ liệu
        loadJobs();
        
        // Setup RecyclerView
        setupRecyclerView();
        
        return view;
    }
    
    private void loadJobs() {
        jobList = dbHelper.getAllJobs();
    }
    
    private void setupRecyclerView() {
        adapter = new AdminJobAdapter(getContext(), jobList, this);
        rvJobs.setLayoutManager(new LinearLayoutManager(getContext()));
        rvJobs.setAdapter(adapter);
        
        updateEmptyState();
    }
    
    private void updateEmptyState() {
        if (jobList.isEmpty()) {
            layoutEmpty.setVisibility(View.VISIBLE);
            rvJobs.setVisibility(View.GONE);
        } else {
            layoutEmpty.setVisibility(View.GONE);
            rvJobs.setVisibility(View.VISIBLE);
        }
    }
    
    @Override
    public void onJobDeleted() {
        updateEmptyState();
        // Notify parent activity to update stats
        if (getActivity() != null) {
            getActivity().recreate();
        }
    }
}
