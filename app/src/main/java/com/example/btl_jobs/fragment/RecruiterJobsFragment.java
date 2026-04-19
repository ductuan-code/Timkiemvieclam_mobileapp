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
import com.example.btl_jobs.adapter.RecruiterJobAdapter;
import com.example.btl_jobs.database.DatabaseHelper;
import com.example.btl_jobs.model.Job;
import com.example.btl_jobs.utils.SessionManager;
import java.util.List;

/**
 * RecruiterJobsFragment - Hiển thị danh sách jobs của Recruiter
 */
public class RecruiterJobsFragment extends Fragment implements RecruiterJobAdapter.OnJobDeletedListener {
    
    private RecyclerView rvJobs;
    private LinearLayout layoutEmpty;
    private RecruiterJobAdapter adapter;
    private List<Job> jobList;
    private DatabaseHelper dbHelper;
    private SessionManager sessionManager;
    private int recruiterId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, 
                            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recruiter_jobs, container, false);
        
        // Khởi tạo
        dbHelper = new DatabaseHelper(getContext());
        sessionManager = new SessionManager(getContext());
        recruiterId = sessionManager.getUserId();
        
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
        jobList = dbHelper.getJobsByRecruiter(recruiterId);
    }
    
    private void setupRecyclerView() {
        adapter = new RecruiterJobAdapter(getContext(), jobList, this);
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
    
    @Override
    public void onResume() {
        super.onResume();
        loadJobs();
        if (adapter != null) {
            adapter = new RecruiterJobAdapter(getContext(), jobList, this);
            rvJobs.setAdapter(adapter);
            updateEmptyState();
        }
    }
}
