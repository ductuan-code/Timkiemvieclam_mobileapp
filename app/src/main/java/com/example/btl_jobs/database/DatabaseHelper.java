package com.example.btl_jobs.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.btl_jobs.model.Job;
import com.example.btl_jobs.model.Application;
import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseHelper - Quản lý SQLite database
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    
    private static final String DATABASE_NAME = "job_finder.db";
    private static final int DATABASE_VERSION = 1;
    
    // Bảng JOBS
    private static final String TABLE_JOBS = "jobs";
    private static final String COL_JOB_ID = "id";
    private static final String COL_JOB_TITLE = "title";
    private static final String COL_JOB_COMPANY = "company";
    private static final String COL_JOB_SALARY = "salary";
    private static final String COL_JOB_LOCATION = "location";
    private static final String COL_JOB_TYPE = "type";
    private static final String COL_JOB_DESCRIPTION = "description";
    private static final String COL_JOB_REQUIREMENTS = "requirements";
    private static final String COL_JOB_BENEFITS = "benefits";
    private static final String COL_JOB_IS_SAVED = "is_saved";
    
    // Bảng APPLICATIONS
    private static final String TABLE_APPLICATIONS = "applications";
    private static final String COL_APP_ID = "id";
    private static final String COL_APP_JOB_ID = "job_id";
    private static final String COL_APP_JOB_TITLE = "job_title";
    private static final String COL_APP_COMPANY = "company";
    private static final String COL_APP_COVER_LETTER = "cover_letter";
    private static final String COL_APP_STATUS = "status";
    private static final String COL_APP_DATE = "applied_date";
    
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng JOBS
        String createJobsTable = "CREATE TABLE " + TABLE_JOBS + " (" +
                COL_JOB_ID + " TEXT PRIMARY KEY," +
                COL_JOB_TITLE + " TEXT," +
                COL_JOB_COMPANY + " TEXT," +
                COL_JOB_SALARY + " TEXT," +
                COL_JOB_LOCATION + " TEXT," +
                COL_JOB_TYPE + " TEXT," +
                COL_JOB_DESCRIPTION + " TEXT," +
                COL_JOB_REQUIREMENTS + " TEXT," +
                COL_JOB_BENEFITS + " TEXT," +
                COL_JOB_IS_SAVED + " INTEGER DEFAULT 0)";
        db.execSQL(createJobsTable);
        
        // Tạo bảng APPLICATIONS
        String createApplicationsTable = "CREATE TABLE " + TABLE_APPLICATIONS + " (" +
                COL_APP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_APP_JOB_ID + " TEXT," +
                COL_APP_JOB_TITLE + " TEXT," +
                COL_APP_COMPANY + " TEXT," +
                COL_APP_COVER_LETTER + " TEXT," +
                COL_APP_STATUS + " TEXT," +
                COL_APP_DATE + " INTEGER)";
        db.execSQL(createApplicationsTable);
        
        // Insert mock data
        insertMockData(db);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOBS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPLICATIONS);
        onCreate(db);
    }
    
    /**
     * Insert dữ liệu mẫu
     */
    private void insertMockData(SQLiteDatabase db) {
        // Job 1
        ContentValues job1 = new ContentValues();
        job1.put(COL_JOB_ID, "1");
        job1.put(COL_JOB_TITLE, "Android Developer");
        job1.put(COL_JOB_COMPANY, "FPT Software");
        job1.put(COL_JOB_SALARY, "15-25 triệu");
        job1.put(COL_JOB_LOCATION, "Hà Nội");
        job1.put(COL_JOB_TYPE, "Full-time");
        job1.put(COL_JOB_DESCRIPTION, "Phát triển ứng dụng Android cho các dự án lớn.");
        job1.put(COL_JOB_REQUIREMENTS, "- 2+ năm kinh nghiệm Android\n- Thành thạo Java/Kotlin");
        job1.put(COL_JOB_BENEFITS, "- Lương thưởng hấp dẫn\n- Bảo hiểm đầy đủ");
        db.insert(TABLE_JOBS, null, job1);
        
        // Job 2
        ContentValues job2 = new ContentValues();
        job2.put(COL_JOB_ID, "2");
        job2.put(COL_JOB_TITLE, "Java Backend Developer");
        job2.put(COL_JOB_COMPANY, "Viettel Solutions");
        job2.put(COL_JOB_SALARY, "20-30 triệu");
        job2.put(COL_JOB_LOCATION, "Hồ Chí Minh");
        job2.put(COL_JOB_TYPE, "Full-time");
        job2.put(COL_JOB_DESCRIPTION, "Phát triển hệ thống backend quy mô lớn.");
        job2.put(COL_JOB_REQUIREMENTS, "- 3+ năm kinh nghiệm Java\n- Thành thạo Spring Boot");
        job2.put(COL_JOB_BENEFITS, "- Lương cao\n- Thưởng theo dự án");
        db.insert(TABLE_JOBS, null, job2);
        
        // Job 3-10 tương tự...
        for (int i = 3; i <= 10; i++) {
            ContentValues job = new ContentValues();
            job.put(COL_JOB_ID, String.valueOf(i));
    // --- APPLICATIONS METHODS ---
    public boolean addApplication(Application application) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (application.getId() == null) application.setId(String.valueOf(System.currentTimeMillis()));
        values.put(COLUMN_APP_ID, application.getId());
        values.put(COLUMN_APP_USER_ID, application.getUserId());
        values.put(COLUMN_APP_JOB_ID, application.getJobId());
        values.put(COLUMN_APP_JOB_TITLE, application.getJobTitle());
        values.put(COLUMN_APP_COMPANY, application.getCompany());
        values.put(COLUMN_APP_COVER_LETTER, application.getCoverLetter());
        values.put(COLUMN_APP_CV_URL, application.getCvUrl());
        values.put(COLUMN_APP_STATUS, application.getStatus());
        values.put(COLUMN_APP_APPLIED_DATE, application.getAppliedDate());
        long result = db.insert(TABLE_APPLICATIONS, null, values);
        db.close();
        return result != -1;
    }

    public List<Application> getAllApplications() {
        List<Application> applicationList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_APPLICATIONS + " ORDER BY " + COLUMN_APP_APPLIED_DATE + " DESC", null);
        if (cursor.moveToFirst()) {
            do {
                applicationList.add(cursorToApplication(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return applicationList;
    }

    private Application cursorToApplication(Cursor cursor) {
    public List<Job> searchJobs(String query) {
        List<Job> jobList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_JOBS + " WHERE " + COLUMN_JOB_TITLE + " LIKE ?", new String[]{"%" + query + "%"});
        if (cursor.moveToFirst()) {
            do {
                jobList.add(cursorToJob(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return jobList;
    }

    public List<Job> getSavedJobs() {
        List<Job> jobList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_JOBS + " WHERE " + COLUMN_JOB_IS_SAVED + " = 1", null);
        if (cursor.moveToFirst()) {
            do {
                jobList.add(cursorToJob(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return jobList;
    }

    public Job getJobById(String jobId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_JOBS + " WHERE " + COLUMN_JOB_ID + " = ?", new String[]{jobId});
        Job job = null;
        if (cursor.moveToFirst()) {
            job = cursorToJob(cursor);
        }
        cursor.close();
        return job;
    }ublic List<Job> getAllJobs() {
        List<Job> jobList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_JOBS, null, null, null, null, null, null);
        
        if (cursor.moveToFirst()) {
            do {
                Job job = new Job();
                job.setId(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_ID)));
                job.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_TITLE)));
                job.setCompany(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_COMPANY)));
                job.setSalary(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_SALARY)));
                job.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_LOCATION)));
                job.setType(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_TYPE)));
                job.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_DESCRIPTION)));
                job.setRequirements(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_REQUIREMENTS)));
                job.setBenefits(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_BENEFITS)));
                job.setSaved(cursor.getInt(cursor.getColumnIndexOrThrow(COL_JOB_IS_SAVED)) == 1);
                
                jobList.add(job);
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
        
        String selection = COL_JOB_TITLE + " LIKE ? OR " + 
                          COL_JOB_COMPANY + " LIKE ? OR " + 
                          COL_JOB_LOCATION + " LIKE ?";
        String[] selectionArgs = {"%" + query + "%", "%" + query + "%", "%" + query + "%"};
        
        Cursor cursor = db.query(TABLE_JOBS, null, selection, selectionArgs, null, null, null);
        
        if (cursor.moveToFirst()) {
            do {
                Job job = new Job();
                job.setId(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_ID)));
                job.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_TITLE)));
                job.setCompany(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_COMPANY)));
                job.setSalary(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_SALARY)));
                job.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_LOCATION)));
                job.setType(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_TYPE)));
                job.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_DESCRIPTION)));
                job.setRequirements(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_REQUIREMENTS)));
                job.setBenefits(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_BENEFITS)));
                job.setSaved(cursor.getInt(cursor.getColumnIndexOrThrow(COL_JOB_IS_SAVED)) == 1);
                
                jobList.add(job);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        return jobList;
    }
    
    /**
     * Lưu job
     */
    public boolean saveJob(String jobId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_JOB_IS_SAVED, 1);
        
        int result = db.update(TABLE_JOBS, values, COL_JOB_ID + "=?", new String[]{jobId});
        return result > 0;
    }
    
    /**
     * Xóa job đã lưu
     */
    public boolean unsaveJob(String jobId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_JOB_IS_SAVED, 0);
        
        int result = db.update(TABLE_JOBS, values, COL_JOB_ID + "=?", new String[]{jobId});
        return result > 0;
    }
    
    /**
     * Kiểm tra job đã được lưu chưa
     */
    public boolean isJobSaved(String jobId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_JOBS, new String[]{COL_JOB_IS_SAVED}, 
                                COL_JOB_ID + "=?", new String[]{jobId}, 
                                null, null, null);
        boolean isSaved = false;
        if (cursor.moveToFirst()) {
            isSaved = cursor.getInt(0) == 1;
        }
        cursor.close();
        return isSaved;
    }
    
    /**
     * Lấy tất cả saved jobs
     */
    public List<Job> getSavedJobs() {
        List<Job> jobList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_JOBS, null, COL_JOB_IS_SAVED + "=1", 
                                null, null, null, null);
        
        if (cursor.moveToFirst()) {
            do {
                Job job = new Job();
                job.setId(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_ID)));
                job.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_TITLE)));
                job.setCompany(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_COMPANY)));
                job.setSalary(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_SALARY)));
                job.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_LOCATION)));
                job.setType(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_TYPE)));
                job.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_DESCRIPTION)));
                job.setRequirements(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_REQUIREMENTS)));
                job.setBenefits(cursor.getString(cursor.getColumnIndexOrThrow(COL_JOB_BENEFITS)));
                job.setSaved(true);
                
                jobList.add(job);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        return jobList;
    }
    
    // ==================== APPLICATIONS METHODS ====================
    
    /**
     * Thêm application
     */
    public boolean addApplication(Application application) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        
        values.put(COL_APP_JOB_ID, application.getJobId());
        values.put(COL_APP_JOB_TITLE, application.getJobTitle());
        values.put(COL_APP_COMPANY, application.getCompany());
        values.put(COL_APP_COVER_LETTER, application.getCoverLetter());
        values.put(COL_APP_STATUS, application.getStatus());
        values.put(COL_APP_DATE, application.getAppliedDate());
        
        long result = db.insert(TABLE_APPLICATIONS, null, values);
        return result != -1;
    }
    
    /**
     * Lấy tất cả applications
     */
    public List<Application> getAllApplications() {
        List<Application> applicationList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_APPLICATIONS, null, null, null, null, null, 
                                COL_APP_DATE + " DESC");
        
        if (cursor.moveToFirst()) {
            do {
                Application application = new Application();
                application.setId(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(COL_APP_ID))));
                application.setJobId(cursor.getString(cursor.getColumnIndexOrThrow(COL_APP_JOB_ID)));
                application.setJobTitle(cursor.getString(cursor.getColumnIndexOrThrow(COL_APP_JOB_TITLE)));
                application.setCompany(cursor.getString(cursor.getColumnIndexOrThrow(COL_APP_COMPANY)));
                application.setCoverLetter(cursor.getString(cursor.getColumnIndexOrThrow(COL_APP_COVER_LETTER)));
                application.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(COL_APP_STATUS)));
                application.setAppliedDate(cursor.getLong(cursor.getColumnIndexOrThrow(COL_APP_DATE)));
                
                applicationList.add(application);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        return applicationList;
    }
}
