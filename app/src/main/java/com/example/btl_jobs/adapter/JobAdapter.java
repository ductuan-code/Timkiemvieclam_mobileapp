package com.example.btl_jobs.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.btl_jobs.R;
import com.example.btl_jobs.activity.JobDetailActivity;
import com.example.btl_jobs.model.Job;
import java.util.ArrayList;
import java.util.List;

/**
 * Adapter cho RecyclerView hiển thị danh sách công việc
 */
public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {
    
    private Context context;
    private List<Job> jobList;
    private List<Job> jobListFull; // Dùng cho search
    private OnJobActionListener listener;
    
    /**
     * Interface để xử lý sự kiện save job
     */
    public interface OnJobActionListener {
        void onSaveClick(Job job, int position);
    }
    
    public JobAdapter(Context context, List<Job> jobList, OnJobActionListener listener) {
        this.context = context;
        this.jobList = jobList;
        this.jobListFull = new ArrayList<>(jobList);
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_job, parent, false);
        return new JobViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        Job job = jobList.get(position);
        holder.bind(job);
    }
    
    @Override
    public int getItemCount() {
        return jobList.size();
    }
    
    /**
     * Filter danh sách job theo query
     */
    public void filter(String query) {
        jobList.clear();
        
        if (query.isEmpty()) {
            jobList.addAll(jobListFull);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (Job job : jobListFull) {
                if (job.getTitle().toLowerCase().contains(lowerCaseQuery) ||
                    job.getCompany().toLowerCase().contains(lowerCaseQuery) ||
                    job.getLocation().toLowerCase().contains(lowerCaseQuery)) {
                    jobList.add(job);
                }
            }
        }
        
        notifyDataSetChanged();
    }
    
    /**
     * ViewHolder cho mỗi item job
     */
    class JobViewHolder extends RecyclerView.ViewHolder {
        
        ImageView ivCompanyLogo;
        TextView tvJobTitle, tvCompany, tvSalary, tvLocation, tvType;
        ImageButton btnSave;
        
        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            
            ivCompanyLogo = itemView.findViewById(R.id.ivCompanyLogo);
            tvJobTitle = itemView.findViewById(R.id.tvJobTitle);
            tvCompany = itemView.findViewById(R.id.tvCompany);
            tvSalary = itemView.findViewById(R.id.tvSalary);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvType = itemView.findViewById(R.id.tvType);
            btnSave = itemView.findViewById(R.id.btnSave);
        }
        
        public void bind(Job job) {
            tvJobTitle.setText(job.getTitle());
            tvCompany.setText(job.getCompany());
            tvSalary.setText(job.getSalary());
            tvLocation.setText(job.getLocation());
            tvType.setText(job.getType());
            
            // Cập nhật icon save
            if (job.isSaved()) {
                btnSave.setImageResource(android.R.drawable.btn_star_big_on);
            } else {
                btnSave.setImageResource(android.R.drawable.btn_star_big_off);
            }
            
            // Load company logo (sử dụng Glide nếu có URL thật)
            // Glide.with(context).load(job.getCompanyLogo()).into(ivCompanyLogo);
            
            // Click vào item -> mở JobDetailActivity
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, JobDetailActivity.class);
                    intent.putExtra(JobDetailActivity.EXTRA_JOB, job);
                    context.startActivity(intent);
                }
            });
            
            // Click vào nút save
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onSaveClick(job, getAdapterPosition());
                    }
                }
            });
        }
    }
}
