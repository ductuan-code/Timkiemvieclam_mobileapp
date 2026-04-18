# 📱 Job Finder Mobile App

---

## 📌 1. Giới thiệu đề tài

Trong bối cảnh nhu cầu tìm kiếm việc làm ngày càng tăng, việc xây dựng một ứng dụng mobile hỗ trợ tìm kiếm và quản lý thông tin tuyển dụng là cần thiết.

Đề tài **Job Finder Mobile App** được xây dựng nhằm:

* Hỗ trợ người dùng tìm kiếm việc làm trên thiết bị di động
* Kết nối người tìm việc và nhà tuyển dụng
* Mô phỏng hệ thống tuyển dụng cơ bản

---

## 🎯 2. Mục tiêu

### 🎯 Mục tiêu tổng quát

Xây dựng ứng dụng mobile hỗ trợ tìm kiếm và quản lý việc làm một cách đơn giản, hiệu quả.

### 🎯 Mục tiêu cụ thể

* Hiển thị danh sách việc làm
* Tìm kiếm và xem chi tiết công việc
* Cho phép người dùng đăng ký, đăng nhập
* Phân quyền người dùng (User / Recruiter / Admin)
* Lưu công việc yêu thích
* Ứng tuyển công việc (mô phỏng)
* Sử dụng database cục bộ để lưu trữ dữ liệu

---

## 👥 3. Actor hệ thống

### 👤 User (Người tìm việc)

* Đăng ký / đăng nhập
* Xem danh sách việc làm
* Tìm kiếm việc làm
* Xem chi tiết job
* Lưu job
* Ứng tuyển job

---

### 🧑‍💼 Recruiter (Nhà tuyển dụng)

* Đăng ký / đăng nhập
* Đăng bài tuyển dụng
* Xóa job
* Xem danh sách ứng viên

---

### 🛠️ Admin

* Đăng nhập
* Xem danh sách người dùng
* Xem tất cả job
* Xóa job

---

## ⚙️ 4. Chức năng hệ thống

### 🔐 Authentication

* Đăng ký (Register)
* Đăng nhập (Login)
* Đăng xuất (Logout)
* Lưu trạng thái đăng nhập (local)
* Phân quyền theo role

---

### 👤 User

* Xem danh sách job
* Tìm kiếm job theo từ khóa
* Xem chi tiết job
* Lưu job yêu thích
* Ứng tuyển job

---

### 🧑‍💼 Recruiter

* Thêm job
* Xóa job
* Xem danh sách ứng viên

---

### 🛠️ Admin

* Xem danh sách user
* Xem tất cả job
* Xóa job

---

## 🗄️ 5. Database

Hệ thống sử dụng **SQLite (local database)** để lưu trữ dữ liệu.

---

### USERS

* id (INTEGER, PRIMARY KEY)
* full_name (TEXT)
* email (TEXT, UNIQUE)
* password (TEXT)
* role (TEXT) → "user" | "recruiter" | "admin"

---

### JOBS

* id (INTEGER, PRIMARY KEY)
* title (TEXT)
* description (TEXT)
* salary (INTEGER)
* location (TEXT)
* recruiter_id (INTEGER)

---

### APPLICATIONS

* id (INTEGER, PRIMARY KEY)
* user_id (INTEGER)
* job_id (INTEGER)
* status (TEXT)

---

### SAVED_JOBS

* id (INTEGER, PRIMARY KEY)
* user_id (INTEGER)
* job_id (INTEGER)

---

## 🏗️ 6. Kiến trúc hệ thống

Mobile App → SQLite Database (Local Storage)

---

## 🚀 7. Công nghệ sử dụng

### 📱 Mobile

- Java (Android)
- Android Studio

### 🗄️ Database

* SQLite

---

## ▶️ 8. Hướng dẫn chạy dự án



## 📁 9. Cấu trúc thư mục

```text
project/
│
├── src/            # source code mobile
├── database/       # SQLite / dữ liệu mẫu
├── assets/         # hình ảnh
├── reports/        # báo cáo
├── slides/         # slide
│
├── README.md
├── .gitignore
```

---

## 👨‍💻 10. Tác giả

* Họ tên: [Điền tên bạn]
* Mã sinh viên: [Điền MSSV]
* Lớp: [Điền lớp]

---

## 📌 11. Kết luận

Ứng dụng giúp:

* Tìm kiếm việc làm dễ dàng trên mobile
* Mô phỏng hệ thống tuyển dụng với nhiều vai trò
* Thực hành phát triển ứng dụng mobile kết hợp database

---

🔥 **Dự án tập trung vào Mobile + SQLite + Authentication + Role (User / Recruiter / Admin), phù hợp với môn Lập trình Mobile**
