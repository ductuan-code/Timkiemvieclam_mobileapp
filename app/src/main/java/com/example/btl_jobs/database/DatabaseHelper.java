package com.example.btl_jobs.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.btl_jobs.model.Job;
import com.example.btl_jobs.model.Application;
import com.example.btl_jobs.model.User;
import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseHelper - Quản lý SQLite database
 * Bảng: USERS, JOBS, APPLICATIONS, SAVED_JOBS
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    
    private static final String DATABASE_NAME = "job_finder.db";
    private static final int DATABASE_VERSION = 2;
    
    // ===== BẢNG USERS =====
    private static final String TABLE_USERS = "users";
    private static final String COL_USER_ID = "id";
    private static final String COL_USER_NAME = "full_name";
    private static final String COL_USER_EMAIL = "email";
    private static final String COL_USER_PASSWORD = "password";
    private static final String COL_USER_ROLE = "role";
    
    // ===== BẢNG JOBS =====
    private static final String TABLE_JOBS = "jobs";
    private static final String COL_JOB_ID = "id";
    private static final String COL_JOB_TITLE = "title";
    private static final String COL_JOB_DESCRIPTION = "description";
    private static final String COL_JOB_SALARY = "salary";
    private static final String COL_JOB_LOCATION = "location";
    private static final String COL_JOB_TYPE = "type";
    private static final String COL_JOB_RECRUITER_ID = "recruiter_id";
    private static final String COL_JOB_COMPANY = "company";
    private static final String COL_JOB_REQUIREMENTS = "requirements";
    private static final String COL_JOB_BENEFITS = "benefits";
    
    // ===== BẢNG APPLICATIONS =====
    private static final String TABLE_APPLICATIONS = "applications";
    private static final String COL_APP_ID = "id";
    private static final String COL_APP_USER_ID = "user_id";
    private static final String COL_APP_JOB_ID = "job_id";
    private static final String COL_APP_STATUS = "status";
    private static final String COL_APP_COVER_LETTER = "cover_letter";
    private static final String COL_APP_DATE = "applied_date";
    
    // ===== BẢNG SAVED_JOBS =====
    private static final String TABLE_SAVED_JOBS = "saved_jobs";
    private static final String COL_SAVED_ID = "id";
    private static final String COL_SAVED_USER_ID = "user_id";
    private static final String COL_SAVED_JOB_ID = "job_id";
    
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng USERS
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_USER_NAME + " TEXT NOT NULL," +
                COL_USER_EMAIL + " TEXT UNIQUE NOT NULL," +
                COL_USER_PASSWORD + " TEXT NOT NULL," +
                COL_USER_ROLE + " TEXT NOT NULL)";
        db.execSQL(createUsersTable);
        
        // Tạo bảng JOBS
        String createJobsTable = "CREATE TABLE " + TABLE_JOBS + " (" +
                COL_JOB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_JOB_TITLE + " TEXT NOT NULL," +
                COL_JOB_DESCRIPTION + " TEXT," +
                COL_JOB_SALARY + " TEXT," +
                COL_JOB_LOCATION + " TEXT," +
                COL_JOB_TYPE + " TEXT," +
                COL_JOB_RECRUITER_ID + " INTEGER," +
                COL_JOB_COMPANY + " TEXT," +
                COL_JOB_REQUIREMENTS + " TEXT," +
                COL_JOB_BENEFITS + " TEXT)";
        db.execSQL(createJobsTable);
        
        // Tạo bảng APPLICATIONS
        String createApplicationsTable = "CREATE TABLE " + TABLE_APPLICATIONS + " (" +
                COL_APP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_APP_USER_ID + " INTEGER NOT NULL," +
                COL_APP_JOB_ID + " INTEGER NOT NULL," +
                COL_APP_STATUS + " TEXT NOT NULL," +
                COL_APP_COVER_LETTER + " TEXT," +
                COL_APP_DATE + " INTEGER)";
        db.execSQL(createApplicationsTable);
        
        // Tạo bảng SAVED_JOBS
        String createSavedJobsTable = "CREATE TABLE " + TABLE_SAVED_JOBS + " (" +
                COL_SAVED_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_SAVED_USER_ID + " INTEGER NOT NULL," +
                COL_SAVED_JOB_ID + " INTEGER NOT NULL," +
                "UNIQUE(" + COL_SAVED_USER_ID + ", " + COL_SAVED_JOB_ID + "))";
        db.execSQL(createSavedJobsTable);
        
        // Insert mock data
        insertMockData(db);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOBS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPLICATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVED_JOBS);
        onCreate(db);
    }
    
    private void insertMockData(SQLiteDatabase db) {
        // ===== INSERT USERS =====
        // Admin
        ContentValues admin = new ContentValues();
        admin.put(COL_USER_NAME, "Admin");
        admin.put(COL_USER_EMAIL, "admin@jobfinder.com");
        admin.put(COL_USER_PASSWORD, "admin123");
        admin.put(COL_USER_ROLE, User.ROLE_ADMIN);
        db.insert(TABLE_USERS, null, admin);
        
        // Recruiter 1
        ContentValues recruiter1 = new ContentValues();
        recruiter1.put(COL_USER_NAME, "FPT Recruiter");
        recruiter1.put(COL_USER_EMAIL, "recruiter@fpt.com");
        recruiter1.put(COL_USER_PASSWORD, "recruiter123");
        recruiter1.put(COL_USER_ROLE, User.ROLE_RECRUITER);
        db.insert(TABLE_USERS, null, recruiter1);
        
        // Recruiter 2
        ContentValues recruiter2 = new ContentValues();
        recruiter2.put(COL_USER_NAME, "Viettel Recruiter");
        recruiter2.put(COL_USER_EMAIL, "recruiter@viettel.com");
        recruiter2.put(COL_USER_PASSWORD, "recruiter123");
        recruiter2.put(COL_USER_ROLE, User.ROLE_RECRUITER);
        db.insert(TABLE_USERS, null, recruiter2);
        
        // User
        ContentValues user = new ContentValues();
        user.put(COL_USER_NAME, "Nguyễn Văn A");
        user.put(COL_USER_EMAIL, "user@gmail.com");
        user.put(COL_USER_PASSWORD, "user123");
        user.put(COL_USER_ROLE, User.ROLE_USER);
        db.insert(TABLE_USERS, null, user);
        
        // ===== INSERT JOBS =====
        // Job 1 - từ recruiter 2 (FPT)
        ContentValues job1 = new ContentValues();
        job1.put(COL_JOB_TITLE, "Android Developer");
        job1.put(COL_JOB_DESCRIPTION, "Phát triển ứng dụng Android cho các dự án lớn của FPT Software.");
        job1.put(COL_JOB_SALARY, "15-25 triệu");
        job1.put(COL_JOB_LOCATION, "Hà Nội");
        job1.put(COL_JOB_TYPE, "Full-time");
        job1.put(COL_JOB_RECRUITER_ID, 2);
        job1.put(COL_JOB_COMPANY, "FPT Software");
        job1.put(COL_JOB_REQUIREMENTS, "- 2+ năm kinh nghiệm Android\n- Thành thạo Java/Kotlin\n- Có kinh nghiệm với Material Design");
        job1.put(COL_JOB_BENEFITS, "- Lương thưởng hấp dẫn\n- Bảo hiểm đầy đủ\n- Môi trường làm việc chuyên nghiệp");
        db.insert(TABLE_JOBS, null, job1);
        
        // Job 2 - từ recruiter 3 (Viettel)
        ContentValues job2 = new ContentValues();
        job2.put(COL_JOB_TITLE, "Java Backend Developer");
        job2.put(COL_JOB_DESCRIPTION, "Phát triển hệ thống backend quy mô lớn cho Viettel Solutions.");
        job2.put(COL_JOB_SALARY, "20-30 triệu");
        job2.put(COL_JOB_LOCATION, "Hồ Chí Minh");
        job2.put(COL_JOB_TYPE, "Full-time");
        job2.put(COL_JOB_RECRUITER_ID, 3);
        job2.put(COL_JOB_COMPANY, "Viettel Solutions");
        job2.put(COL_JOB_REQUIREMENTS, "- 3+ năm kinh nghiệm Java\n- Thành thạo Spring Boot\n- Có kinh nghiệm với Microservices");
        job2.put(COL_JOB_BENEFITS, "- Lương cao\n- Thưởng theo dự án\n- Du lịch hàng năm");
        db.insert(TABLE_JOBS, null, job2);
        
        // Thêm các job khác
        String[] titles = {"Frontend Developer", "DevOps Engineer", "UI/UX Designer", "Data Analyst"};
        String[] companies = {"VNG Corporation", "Tiki", "Shopee", "Grab"};
        String[] locations = {"Hà Nội", "Đà Nẵng", "Hồ Chí Minh", "Hà Nội"};
        
        for (int i = 0; i < titles.length; i++) {
            ContentValues job = new ContentValues();
            job.put(COL_JOB_TITLE, titles[i]);
            job.put(COL_JOB_DESCRIPTION, "Mô tả công việc cho vị trí " + titles[i]);
            job.put(COL_JOB_SALARY, (12 + i * 3) + "-" + (20 + i * 5) + " triệu");
            job.put(COL_JOB_LOCATION, locations[i]);
            job.put(COL_JOB_TYPE, "Full-time");
            job.put(COL_JOB_RECRUITER_ID, 2 + (i % 2)); // Xen kẽ giữa recruiter 2 và 3
            job.put(COL_JOB_COMPANY, companies[i]);
            job.put(COL_JOB_REQUIREMENTS, "Yêu cầu cho vị trí " + titles[i]);
            job.put(COL_JOB_BENEFITS, "Quyền lợi hấp dẫn");
            db.insert(TABLE_JOBS, null, job);
        }
    }
    
    // ==================== USER METHODS ====================
    
    /**
     * Đăng ký user mới
     */
    public long registerUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_NAME, user.getFullName());
        values.put(COL_USER_EMAIL, user.getEmail());
        values.put(COL_USER_PASSWORD, user.getPassword());
        values.put(COL_USER_ROLE, user.getRole());
        return db.insert(TABLE_USERS, null, values);
    }
    
    /**
     * Đăng nhập
     */
    public User login(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COL_USER_EMAIL + "=? AND " + COL_USER_PASSWORD + "=?";
        String[] selectionArgs = {email, password};
        
        Cursor cursor = db.query(TABLE_USERS, null, selection, selectionArgs, null, null, null);
        User user = null;
        if (cursor.moveToFirst()) {
            user = cursorToUser(cursor);
        }
        cursor.close();
        return user;
    }
    
    /**
     * Kiểm tra email đã tồn tại chưa
     */
    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COL_USER_ID}, 
                COL_USER_EMAIL + "=?", new String[]{email}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    
    /**
     * Lấy tất cả users (cho Admin)
     */
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, null, null, null, null, null);
        
        if (cursor.moveToFirst()) {
            do {
                userList.add(cursorToUser(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return userList;
    }
    
    private User cursorToUser(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_USER_ID)));
        user.setFullName(cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_NAME)));
        user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_EMAIL)));
        user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_PASSWORD)));
        user.setRole(cursor.getString(cursor.getColumnIndexOrThrow(COL_USER_ROLE)));
        return user;
    }
    
    // ==================== JOB METHODS ====================
    
    /**
     * Thêm job mới (Recruiter)
     */
    public long addJob(Job job) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_JOB_TITLE, job.getTitle());
        values.put(COL_JOB_DESCRIPTION, job.getDescription());
        values.put(COL_JOB_SALARY, job.getSalary());
        values.put(COL_JOB_LOCATION, job.getLocation());
        values.put(COL_JOB_TYPE, job.getType());
        values.put(COL_JOB_RECRUITER_ID, job.getRecruiterId());
        values.put(COL_JOB_COMPANY, job.getCompany());
        values.put(COL_JOB_REQUIREMENTS, job.getRequirements());
        values.put(COL_JOB_BENEFITS, job.getBenefits());
        return db.insert(TABLE_JOBS, null, values);
    }
    
    /**
     * Lấy tất cả jobs
     */
    public List<Job> getAllJobs() {
        List<Job> jobList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_JOBS, null, null, null, null, null, COL_JOB_ID + " DESC");
        
        if (cursor.moveToFirst()) {
            do {
                jobList.add(cursorToJob(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return jobList;
    }
    
    /**
     * Lấy jobs của một recruiter
     */
    public List<Job> getJobsByRecruiter(int recruiterId) {
        List<Job> jobList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_JOBS, null, COL_JOB_RECRUITER_ID + "=?", 
                new String[]{String.valueOf(recruiterId)}, null, null, COL_JOB_ID + " DESC");
        
        if (cursor.moveToFirst()) {
            do {
                jobList.add(cursorToJob(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return jobList;
    }
    
    /**
     * Tìm kiếm jobs
     */
    public List<Job> searchJobs(String query) {
        List<Job> jobList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COL_JOB_TITLE + " LIKE ? OR " + COL_JOB_COMPANY + " LIKE ? OR " + COL_JOB_LOCATION + " LIKE ?";
        String[] args = new String[]{"%" + query + "%", "%" + query + "%", "%" + query + "%"};
        
        Cursor cursor = db.query(TABLE_JOBS, null, selection, args, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                jobList.add(cursorToJob(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return jobList;
    }
    
    /**
     * Lấy job theo ID
     */
    public Job getJobById(int jobId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_JOBS, null, COL_JOB_ID + "=?", 
                new String[]{String.valueOf(jobId)}, null, null, null);
        Job job = null;
        if (cursor.moveToFirst()) {
            job = cursorToJob(cursor);
        }
        cursor.close();
        return job;
    }
    
    /**
     * Xóa job
     */
    public boolean deleteJob(int jobId) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Xóa cả saved jobs và applications liên quan
        db.delete(TABLE_SAVED_JOBS, COL_SAVED_JOB_ID + "=?", new String[]{String.valueOf(jobId)});
        db.delete(TABLE_APPLICATIONS, COL_APP_JOB_ID + "=?", new String[]{String.valueOf(jobId)});
        return db.delete(TABLE_JOBS, COL_JOB_ID + "=?", new String[]{String.valueOf(jobId)}) > 0;
    }
    
    private Job cursorToJob(Cursor cursor) {
        Job job = new Job();
        job.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_JOB_ID)));
        job.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_TITLE)));
        job.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_DESCRIPTION)));
        job.setSalary(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_SALARY)));
        job.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_LOCATION)));
        job.setType(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_TYPE)));
        job.setRecruiterId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_JOB_RECRUITER_ID)));
        job.setCompany(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_COMPANY)));
        job.setRequirements(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_REQUIREMENTS)));
        job.setBenefits(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_BENEFITS)));
        return job;
    }
    
    // ==================== SAVED JOBS METHODS ====================
    
    /**
     * Lưu job
     */
    public boolean saveJob(int userId, int jobId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_SAVED_USER_ID, userId);
        values.put(COL_SAVED_JOB_ID, jobId);
        return db.insert(TABLE_SAVED_JOBS, null, values) != -1;
    }
    
    /**
     * Bỏ lưu job
     */
    public boolean unsaveJob(int userId, int jobId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_SAVED_JOBS, 
                COL_SAVED_USER_ID + "=? AND " + COL_SAVED_JOB_ID + "=?", 
                new String[]{String.valueOf(userId), String.valueOf(jobId)}) > 0;
    }
    
    /**
     * Kiểm tra job đã được lưu chưa
     */
    public boolean isJobSaved(int userId, int jobId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SAVED_JOBS, null, 
                COL_SAVED_USER_ID + "=? AND " + COL_SAVED_JOB_ID + "=?", 
                new String[]{String.valueOf(userId), String.valueOf(jobId)}, null, null, null);
        boolean isSaved = cursor.getCount() > 0;
        cursor.close();
        return isSaved;
    }
    
    /**
     * Lấy danh sách jobs đã lưu của user
     */
    public List<Job> getSavedJobs(int userId) {
        List<Job> jobList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        String query = "SELECT j.* FROM " + TABLE_JOBS + " j " +
                "INNER JOIN " + TABLE_SAVED_JOBS + " s ON j." + COL_JOB_ID + " = s." + COL_SAVED_JOB_ID +
                " WHERE s." + COL_SAVED_USER_ID + " = ?";
        
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            do {
                jobList.add(cursorToJob(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return jobList;
    }
    
    // ==================== APPLICATION METHODS ====================
    
    /**
     * Ứng tuyển job
     */
    public long applyJob(Application application) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_APP_USER_ID, application.getUserId());
        values.put(COL_APP_JOB_ID, application.getJobId());
        values.put(COL_APP_STATUS, application.getStatus());
        values.put(COL_APP_COVER_LETTER, application.getCoverLetter());
        values.put(COL_APP_DATE, application.getAppliedDate());
        return db.insert(TABLE_APPLICATIONS, null, values);
    }
    
    /**
     * Kiểm tra đã ứng tuyển job chưa
     */
    public boolean hasApplied(int userId, int jobId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_APPLICATIONS, null, 
                COL_APP_USER_ID + "=? AND " + COL_APP_JOB_ID + "=?", 
                new String[]{String.valueOf(userId), String.valueOf(jobId)}, null, null, null);
        boolean hasApplied = cursor.getCount() > 0;
        cursor.close();
        return hasApplied;
    }
    
    /**
     * Lấy danh sách applications của user
     */
    public List<Application> getUserApplications(int userId) {
        List<Application> applicationList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        String query = "SELECT a.*, j." + COL_JOB_TITLE + ", j." + COL_JOB_COMPANY + 
                " FROM " + TABLE_APPLICATIONS + " a " +
                "INNER JOIN " + TABLE_JOBS + " j ON a." + COL_APP_JOB_ID + " = j." + COL_JOB_ID +
                " WHERE a." + COL_APP_USER_ID + " = ? ORDER BY a." + COL_APP_DATE + " DESC";
        
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            do {
                Application app = new Application();
                app.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_APP_ID)));
                app.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_APP_USER_ID)));
                app.setJobId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_APP_JOB_ID)));
                app.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(COL_APP_STATUS)));
                app.setCoverLetter(cursor.getString(cursor.getColumnIndexOrThrow(COL_APP_COVER_LETTER)));
                app.setAppliedDate(cursor.getLong(cursor.getColumnIndexOrThrow(COL_APP_DATE)));
                app.setJobTitle(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_TITLE)));
                app.setCompany(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_COMPANY)));
                applicationList.add(app);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return applicationList;
    }
    
    /**
     * Lấy danh sách ứng viên cho jobs của recruiter
     */
    public List<Application> getApplicationsByRecruiter(int recruiterId) {
        List<Application> applicationList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        String query = "SELECT a.*, j." + COL_JOB_TITLE + ", j." + COL_JOB_COMPANY + ", u." + COL_USER_NAME + ", u." + COL_USER_EMAIL +
                " FROM " + TABLE_APPLICATIONS + " a " +
                "INNER JOIN " + TABLE_JOBS + " j ON a." + COL_APP_JOB_ID + " = j." + COL_JOB_ID +
                " INNER JOIN " + TABLE_USERS + " u ON a." + COL_APP_USER_ID + " = u." + COL_USER_ID +
                " WHERE j." + COL_JOB_RECRUITER_ID + " = ? ORDER BY a." + COL_APP_DATE + " DESC";
        
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(recruiterId)});
        if (cursor.moveToFirst()) {
            do {
                Application app = new Application();
                app.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_APP_ID)));
                app.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_APP_USER_ID)));
                app.setJobId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_APP_JOB_ID)));
                app.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(COL_APP_STATUS)));
                app.setCoverLetter(cursor.getString(cursor.getColumnIndexOrThrow(COL_APP_COVER_LETTER)));
                app.setAppliedDate(cursor.getLong(cursor.getColumnIndexOrThrow(COL_APP_DATE)));
                app.setJobTitle(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_TITLE)));
                app.setCompany(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_COMPANY)));
                applicationList.add(app);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return applicationList;
    }
}
