package com.example.btl_jobs.model;

/**
 * Model class đại diện cho Application (đơn ứng tuyển)
 */
public class Application {
    // Status constants
    public static final String STATUS_PENDING = "Pending";
    public static final String STATUS_REVIEWING = "Reviewing";
    public static final String STATUS_ACCEPTED = "Accepted";
    public static final String STATUS_REJECTED = "Rejected";
    
    private int id;
    private int userId;
    private int jobId;
    private String jobTitle;
    private String company;
    private String coverLetter;
    private String status; // Pending, Reviewing, Accepted, Rejected
    private long appliedDate;

    // Constructor mặc định
    public Application() {
    }

    // Constructor đầy đủ
    public Application(int id, int userId, int jobId, String jobTitle, 
                      String company, String coverLetter, String status) {
        this.id = id;
        this.userId = userId;
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.company = company;
        this.coverLetter = coverLetter;
        this.status = status;
        this.appliedDate = System.currentTimeMillis();
    }

    // Constructor không có id (dùng khi insert mới)
    public Application(int userId, int jobId, String jobTitle, 
                      String company, String coverLetter, String status) {
        this.userId = userId;
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.company = company;
        this.coverLetter = coverLetter;
        this.status = status;
        this.appliedDate = System.currentTimeMillis();
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getAppliedDate() {
        return appliedDate;
    }

    public void setAppliedDate(long appliedDate) {
        this.appliedDate = appliedDate;
    }
}
