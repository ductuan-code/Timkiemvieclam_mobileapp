package com.example.btl_jobs.model;

/**
 * Model class đại diện cho Application (đơn ứng tuyển)
 */
public class Application {
    private String id;
    private String userId;
    private String jobId;
    private String jobTitle;
    private String company;
    private String coverLetter;
    private String cvUrl;
    private String status; // Pending, Accepted, Rejected
    private long appliedDate;

    // Constructor mặc định
    public Application() {
    }

    // Constructor đầy đủ
    public Application(String id, String userId, String jobId, String jobTitle, 
                      String company, String coverLetter, String cvUrl, String status) {
        this.id = id;
        this.userId = userId;
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.company = company;
        this.coverLetter = coverLetter;
        this.cvUrl = cvUrl;
        this.status = status;
        this.appliedDate = System.currentTimeMillis();
    }

    // Getters và Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
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

    public String getCvUrl() {
        return cvUrl;
    }

    public void setCvUrl(String cvUrl) {
        this.cvUrl = cvUrl;
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
