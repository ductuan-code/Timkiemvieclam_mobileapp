package com.example.btl_jobs.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.btl_jobs.R;
import com.example.btl_jobs.activity.JobDetailActivity;
import com.example.btl_jobs.database.DatabaseHelper;
import com.example.btl_jobs.model.Job;
import java.util.List;

/**
 * Adapter cho danh sách jobs của Recruiter
 */
public class RecruiterJobAdapter extends RecyclerView.Adapter<RecruiterJobAdapter.ViewHolder> {
    
    private Context context;
    private List<Job> jobList;
    private DatabaseHelper dbHelper;
    private OnJobDeletedListener listener;
    
    public interface OnJobDeletedListener {
        void onJobDeleted();
    }
    
    public RecruiterJobAdapter(Context context, List<Job> jobList, OnJobDeletedListener listener) {
        this.context = context;
        this.jobList = jobList;
        this.dbHelper = new DatabaseHelper(context);
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recruiter_job, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Job job = jobList.get(position);
        holder.bind(job);
    }
    
    @Override
    public int getItemCount() {
        return jobList.size();
    }
    
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvJobTitle, tvCompany, tvSalary, tvLocation, tvType;
        ImageButton btnDelete;
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJobTitle = itemView.findViewById(R.id.tvJobTitle);
            tvCompany = itemView.findViewById(R.id.tvCompany);
            tvSalary = itemView.findViewById(R.id.tvSalary);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvType = itemView.findViewById(R.id.tvType);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
        
        public void bind(Job job) {
            tvJobTitle.setText(job.getTitle());
            tvCompany.setText(job.getCompany());
            tvSalary.setText(job.getSalary());
            tvLocation.setText(job.getLocation());
            tvType.setText(job.getType());
            
            // Click vào item -> xem chi tiết
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, JobDetailActivity.class);
                intent.putExtra(JobDetailActivity.EXTRA_JOB, job);
                context.startActivity(intent);
            });
            
            // Click delete
            btnDelete.setOnClickListener(v -> showDeleteDialog(job, getAdapterPosition()));
        }
    }
    
    private void showDeleteDialog(Job job, int position) {
        new AlertDialog.Builder(context)
            .setTitle("Xóa tin tuyển dụng")
            .setMessage("Bạn có chắc muốn xóa tin \"" + job.getTitle() + "\"?")
            .setPositiveButton("Xóa", (dialog, which) -> {
                boolean success = dbHelper.deleteJob(job.getId());
                if (success) {
                    jobList.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Đã xóa tin tuyển dụng", Toast.LENGTH_SHORT).show();
                    if (listener != null) {
                        listener.onJobDeleted();
                    }
                } else {
                    Toast.makeText(context, "Lỗi khi xóa", Toast.LENGTH_SHORT).show();
                }
            })
            .setNegativeButton("Hủy", null)
            .show();
    }
}
