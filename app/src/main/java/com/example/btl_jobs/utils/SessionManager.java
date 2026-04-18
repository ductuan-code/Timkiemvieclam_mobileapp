package com.example.btl_jobs.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.btl_jobs.model.User;

/**
 * SessionManager - Quản lý phiên đăng nhập của user
 * Lưu trữ thông tin user trong SharedPreferences
 */
public class SessionManager {
    private static final String PREF_NAME = "JobFinderSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_USER_EMAIL = "userEmail";
    private static final String KEY_USER_ROLE = "userRole";
    
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Context context;
    
    public SessionManager(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }
    
    /**
     * Lưu thông tin đăng nhập
     */
    public void createLoginSession(User user) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putInt(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_NAME, user.getFullName());
        editor.putString(KEY_USER_EMAIL, user.getEmail());
        editor.putString(KEY_USER_ROLE, user.getRole());
        editor.apply();
    }
    
    /**
     * Kiểm tra user đã đăng nhập chưa
     */
    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }
    
    /**
     * Lấy thông tin user hiện tại
     */
    public User getCurrentUser() {
        if (!isLoggedIn()) {
            return null;
        }
        
        User user = new User();
        user.setId(prefs.getInt(KEY_USER_ID, -1));
        user.setFullName(prefs.getString(KEY_USER_NAME, ""));
        user.setEmail(prefs.getString(KEY_USER_EMAIL, ""));
        user.setRole(prefs.getString(KEY_USER_ROLE, User.ROLE_USER));
        return user;
    }
    
    /**
     * Lấy user ID
     */
    public int getUserId() {
        return prefs.getInt(KEY_USER_ID, -1);
    }
    
    /**
     * Lấy user role
     */
    public String getUserRole() {
        return prefs.getString(KEY_USER_ROLE, User.ROLE_USER);
    }
    
    /**
     * Kiểm tra role
     */
    public boolean isUser() {
        return User.ROLE_USER.equals(getUserRole());
    }
    
    public boolean isRecruiter() {
        return User.ROLE_RECRUITER.equals(getUserRole());
    }
    
    public boolean isAdmin() {
        return User.ROLE_ADMIN.equals(getUserRole());
    }
    
    /**
     * Đăng xuất
     */
    public void logout() {
        editor.clear();
        editor.apply();
    }
}
