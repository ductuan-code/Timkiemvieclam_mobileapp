package com.example.btl_jobs.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.btl_jobs.R;
import com.example.btl_jobs.model.Application;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Adapter cho RecyclerView hiển thị danh sách đơn ứng tuyển
 */
public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ApplicationViewHolder> {
    
    private Context context;
    private List<Application> applicationList;
    
    public ApplicationAdapter(Context context, List<Application> applicationList) {
        this.context = context;
        this.applicationList = applicationList;
    }
    
    @NonNull
    @Override
    public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_application, parent, false);
        return new ApplicationViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ApplicationViewHolder holder, int position) {
        Application application = applicationList.get(position);
        holder.bind(application);
    }
    
    @Override
    public int getItemCount() {
        return applicationList.size();
    }
    
    /**
     * ViewHolder cho mỗi item application
     */
    class ApplicationViewHolder extends RecyclerView.ViewHolder {
        
        TextView tvJobTitle, tvCompany, tvAppliedDate, tvStatus;
        
        public ApplicationViewHolder(@NonNull View itemView) {
            super(itemView);
            
            tvJobTitle = itemView.findViewById(R.id.tvJobTitle);
            tvCompany = itemView.findViewById(R.id.tvCompany);
            tvAppliedDate = itemView.findViewById(R.id.tvAppliedDate);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
        
        public void bind(Application application) {
            tvJobTitle.setText(application.getJobTitle());
            tvCompany.setText(application.getCompany());
            
            // Format ngày ứng tuyển
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String dateStr = sdf.format(new Date(application.getAppliedDate()));
            tvAppliedDate.setText("Ứng tuyển: " + dateStr);
            
            // Hiển thị status với màu tương ứng
            String status = application.getStatus();
            tvStatus.setText(status);
            
            switch (status) {
                case "Pending":
                    tvStatus.setBackgroundColor(Color.parseColor("#FFA500")); // Orange
                    break;
                case "Accepted":
                    tvStatus.setBackgroundColor(Color.parseColor("#4CAF50")); // Green
                    break;
                case "Rejected":
                    tvStatus.setBackgroundColor(Color.parseColor("#F44336")); // Red
                    break;
            }
        }
    }
}
