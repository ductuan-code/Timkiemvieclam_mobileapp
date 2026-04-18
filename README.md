# 📱 Job Finder Mobile App

## 📌 1. Giới thiệu đề tài

Trong thời đại công nghệ hiện nay, nhu cầu tìm kiếm việc làm ngày càng cao. Việc xây dựng một ứng dụng mobile hỗ trợ tìm kiếm và quản lý thông tin việc làm là cần thiết.

Đề tài **Job Finder Mobile App** được xây dựng nhằm:

* Hỗ trợ người dùng tìm kiếm việc làm trên thiết bị di động
* Giúp quản lý thông tin công việc một cách trực quan
* Mô phỏng hệ thống tuyển dụng đơn giản

---

## 🎯 2. Mục tiêu

### 🎯 Mục tiêu tổng quát

Xây dựng ứng dụng mobile hỗ trợ tìm kiếm việc làm đơn giản, dễ sử dụng.

### 🎯 Mục tiêu cụ thể

* Hiển thị danh sách việc làm
* Cho phép tìm kiếm và xem chi tiết job
* Lưu job yêu thích
* Ứng tuyển công việc (mô phỏng)
* Thiết kế database phù hợp

---

## 👥 3. Actor hệ thống

### 👤 User (Người tìm việc)

* Xem danh sách job
* Tìm kiếm job
* Xem chi tiết job
* Lưu job
* Apply job

---

## ⚙️ 4. Chức năng hệ thống

### 👤 User

* Xem danh sách việc làm
* Tìm kiếm theo từ khóa
* Xem chi tiết công việc
* Lưu công việc yêu thích
* Ứng tuyển (lưu trạng thái)

---

## 🗄️ 5. Database

Hệ thống sử dụng database local (SQLite hoặc JSON).

### USERS

* id
* full_name
* email

### JOBS

* id
* title
* description
* salary
* location

### APPLICATIONS

* id
* user_id
* job_id
* status

### SAVED_JOBS

* id
* user_id
* job_id

---

## 🏗️ 6. Kiến trúc hệ thống

Mobile App → Local Database (SQLite)

---

## 🚀 7. Công nghệ sử dụng

### 📱 Mobile

* React Native / Flutter / Android Kotlin

### 🗄️ Database

* SQLite / Local Storage

---

## ▶️ 8. Hướng dẫn chạy dự án

```bash
npm install
npm start
```

---

## 📁 9. Cấu trúc thư mục

```text
project/
│
├── src/            # source code mobile
├── database/       # file database / mock data
├── assets/         # hình ảnh
├── reports/        # báo cáo
├── slides/         # slide
│
├── README.md
├── .gitignore
```

---

## 👨‍💻 10. Tác giả

* Họ tên: Nguyễn Đức Tuấn
* Mã sinh viên: 12523116
* Lớp: 12323W.1

---

## 📌 11. Kết luận

Ứng dụng giúp:

* Tìm kiếm việc làm nhanh chóng
* Trải nghiệm mobile đơn giản
* Làm quen với phát triển ứng dụng mobile và database

---

🔥 **Dự án tập trung vào phát triển mobile và thiết kế database, phù hợp với môn học cơ bản**
