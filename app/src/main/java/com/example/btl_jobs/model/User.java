package com.example.btl_jobs.model;

/**
 * Model class đại diện cho User trong hệ thống
 */
public class User {
    private String id;
    private String email;
    private String fullName;
    private String phone;
    private String address;
    private String avatarUrl;
    private long createdAt;

    // Constructor mặc định (bắt buộc cho Firebase)
    public User() {
    }

    // Constructor đầy đủ
    public User(String id, String email, String fullName, String phone, String address, String avatarUrl) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
        this.avatarUrl = avatarUrl;
        this.createdAt = System.currentTimeMillis();
    }

    // Getters và Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
