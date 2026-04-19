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
import com.example.btl_jobs.adapter.UserAdapter;
import com.example.btl_jobs.database.DatabaseHelper;
import com.example.btl_jobs.model.User;
import java.util.List;

/**
 * AdminUsersFragment - Hiển thị danh sách users
 */
public class AdminUsersFragment extends Fragment {
    
    private RecyclerView rvUsers;
    private LinearLayout layoutEmpty;
    private UserAdapter adapter;
    private List<User> userList;
    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, 
                            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_users, container, false);
        
        // Khởi tạo
        dbHelper = new DatabaseHelper(getContext());
        
        // Ánh xạ views
        rvUsers = view.findViewById(R.id.rvUsers);
        layoutEmpty = view.findViewById(R.id.layoutEmpty);
        
        // Load dữ liệu
        loadUsers();
        
        // Setup RecyclerView
        setupRecyclerView();
        
        return view;
    }
    
    private void loadUsers() {
        userList = dbHelper.getAllUsers();
    }
    
    private void setupRecyclerView() {
        adapter = new UserAdapter(getContext(), userList);
        rvUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        rvUsers.setAdapter(adapter);
        
        updateEmptyState();
    }
    
    private void updateEmptyState() {
        if (userList.isEmpty()) {
            layoutEmpty.setVisibility(View.VISIBLE);
            rvUsers.setVisibility(View.GONE);
        } else {
            layoutEmpty.setVisibility(View.GONE);
            rvUsers.setVisibility(View.VISIBLE);
        }
    }
}
