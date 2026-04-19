package com.example.btl_jobs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.btl_jobs.R;
import com.example.btl_jobs.model.User;
import java.util.List;

/**
 * Adapter cho danh sách users (Admin)
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    
    private Context context;
    private List<User> userList;
    
    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user);
    }
    
    @Override
    public int getItemCount() {
        return userList.size();
    }
    
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName, tvUserEmail, tvUserRole;
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvUserEmail = itemView.findViewById(R.id.tvUserEmail);
            tvUserRole = itemView.findViewById(R.id.tvUserRole);
        }
        
        public void bind(User user) {
            tvUserName.setText(user.getFullName());
            tvUserEmail.setText(user.getEmail());
            
            // Hiển thị role với màu khác nhau
            String roleText = "";
            switch (user.getRole()) {
                case User.ROLE_ADMIN:
                    roleText = "Admin";
                    break;
                case User.ROLE_RECRUITER:
                    roleText = "Recruiter";
                    break;
                case User.ROLE_USER:
                    roleText = "User";
                    break;
            }
            tvUserRole.setText(roleText);
        }
    }
}
