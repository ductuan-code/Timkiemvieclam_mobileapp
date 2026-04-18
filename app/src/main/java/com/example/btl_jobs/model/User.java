package com.example.btl_jobs.model;

/**
 * Model class đại diện cho User trong hệ thống
 * Hỗ trợ 3 roles: user, recruiter, admin
 */
public class User {
    // Role constants
    public static final String ROLE_USER = "user";
    public static final String ROLE_RECRUITER = "recruiter";
    public static final String ROLE_ADMIN = "admin";
    
    private int id;
    private String fullName;
    private String email;
    private String password;
    private String role; // "user" | "recruiter" | "admin"
    private long createdAt;

    // Constructor mặc định
    public User() {
    }

    // Constructor đầy đủ
    public User(int id, String fullName, String email, String password, String role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = System.currentTimeMillis();
    }

    // Constructor không có id (dùng khi insert mới)
    public User(String fullName, String email, String password, String role) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = System.currentTimeMillis();
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    // Helper methods
    public boolean isUser() {
        return ROLE_USER.equals(role);
    }

    public boolean isRecruiter() {
        return ROLE_RECRUITER.equals(role);
    }

    public boolean isAdmin() {
        return ROLE_ADMIN.equals(role);
    }
}
