package com.example.btl_jobs.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.btl_jobs.R;
import com.example.btl_jobs.adapter.JobAdapter;
import com.example.btl_jobs.database.DatabaseHelper;
import com.example.btl_jobs.model.Job;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;

/**
 * HomeFragment - Màn hình tìm kiếm việc làm
 */
public class HomeFragment extends Fragment implements JobAdapter.OnJobActionListener {
    
    private RecyclerView rvJobs;
    private TextView tvEmpty;
    private TextInputEditText etSearch;
    private JobAdapter jobAdapter;
    private List<Job> jobList;
    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, 
                            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        
        // Khởi tạo database
        dbHelper = new DatabaseHelper(getContext());
        
        // Ánh xạ views
        initViews(view);
        
        // Load dữ liệu
        loadJobs();
        
        // Setup RecyclerView
        setupRecyclerView();
        
        // Setup search
        setupSearch();
        
        return view;
    }
    
    /**
     * Ánh xạ các view
     */
    private void initViews(View view) {
        rvJobs = view.findViewById(R.id.rvJobs);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        etSearch = view.findViewById(R.id.etSearch);
    }
    
    /**
     * Load danh sách công việc từ SQLite
     */
    private void loadJobs() {
        jobList = dbHelper.getAllJobs();
    }
    
    /**
     * Setup RecyclerView
     */
    private void setupRecyclerView() {
        jobAdapter = new JobAdapter(getContext(), jobList, this);
        rvJobs.setLayoutManager(new LinearLayoutManager(getContext()));
        rvJobs.setAdapter(jobAdapter);
        
        updateEmptyState();
    }
    
    /**
     * Setup search functionality
     */
    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim();
                if (query.isEmpty()) {
                    jobList = dbHelper.getAllJobs();
                } else {
                    jobList = dbHelper.searchJobs(query);
                }
                jobAdapter = new JobAdapter(getContext(), jobList, HomeFragment.this);
                rvJobs.setAdapter(jobAdapter);
                updateEmptyState();
            }
            
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    
    /**
     * Cập nhật empty state
     */
    private void updateEmptyState() {
        if (jobList.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            rvJobs.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            rvJobs.setVisibility(View.VISIBLE);
        }
    }
    
    /**
     * Xử lý khi click nút save
     */
    @Override
    public void onSaveClick(Job job, int position) {
        if (job.isSaved()) {
            // Unsave
            dbHelper.unsaveJob(job.getId());
            job.setSaved(false);
        } else {
            // Save
            dbHelper.saveJob(job.getId());
            job.setSaved(true);
        }
        
        jobAdapter.notifyItemChanged(position);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        // Reload dữ liệu khi quay lại fragment
        loadJobs();
        if (jobAdapter != null) {
            jobAdapter = new JobAdapter(getContext(), jobList, this);
            rvJobs.setAdapter(jobAdapter);
        }
    }
}
