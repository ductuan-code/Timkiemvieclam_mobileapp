package com.example.btl_jobs.model;

import java.io.Serializable;

/**
 * Model class đại diện cho Job (công việc)
 */
public class Job implements Serializable {
    private int id;
    private String title;
    private String description;
    private String salary;
    private String location;
    private String type; // Full-time, Part-time, Remote, etc.
    private int recruiterId; // ID của recruiter đăng job
    private String company;
    private String requirements;
    private String benefits;
    private long postedDate;
    private boolean isSaved;

    // Constructor mặc định
    public Job() {
    }

    // Constructor đầy đủ
    public Job(int id, String title, String description, String salary, String location, 
               String type, int recruiterId, String company, String requirements, String benefits) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.location = location;
        this.type = type;
        this.recruiterId = recruiterId;
        this.company = company;
        this.requirements = requirements;
        this.benefits = benefits;
        this.postedDate = System.currentTimeMillis();
        this.isSaved = false;
    }

    // Constructor không có id (dùng khi insert mới)
    public Job(String title, String description, String salary, String location, 
               String type, int recruiterId, String company, String requirements, String benefits) {
        this.title = title;
        this.description = description;
        this.salary = salary;
        this.location = location;
        this.type = type;
        this.recruiterId = recruiterId;
        this.company = company;
        this.requirements = requirements;
        this.benefits = benefits;
        this.postedDate = System.currentTimeMillis();
        this.isSaved = false;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(int recruiterId) {
        this.recruiterId = recruiterId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public long getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(long postedDate) {
        this.postedDate = postedDate;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }
}
