# 🎬 YouTube Clone - Backend

A RESTful backend for a YouTube Clone built with Java Spring Boot. It provides secure JWT authentication, video upload, video streaming, likes, comments, view counting, and channel management.

---

# 📌 Project Overview

This backend exposes REST APIs for a YouTube Clone application.

It handles:

- User authentication
- JWT authorization
- Video upload
- Thumbnail upload
- Video streaming
- View counting
- Like / Dislike
- Comments
- Channel information
- PostgreSQL database operations

---

# 🚀 Features

## 🔐 Authentication

- User Registration
- User Login
- BCrypt Password Encryption
- JWT Authentication
- Spring Security
- Protected APIs
- Stateless Authentication

---

## 🎥 Video Management

- Upload Videos
- Upload Thumbnails
- Store video metadata in PostgreSQL
- Stream videos
- Update video title
- Update video description
- Update video thumbnail
- Automatically count video views

---

## 👍 Engagement

- Like Video
- Dislike Video
- Comment on Videos
- View comments

---

## 👤 Channel

- Channel Name
- Profile Image
- Display uploader information
- Owner verification before editing videos

---

## 🛠 Tech Stack

- Java 21
- Spring Boot 3
- Spring Security
- JWT
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven

---

# 📂 Project Structure

backend/
├── controller/
│ ├── UserController.java
│ ├── VideoController.java
│ ├── CommentController.java
│ └── LikeController.java
│
├── service/
│ ├── UserService.java
│ ├── VideoService.java
│ ├── CommentService.java
│ └── LikeService.java
│
├── repository/
│ ├── UserRepository.java
│ ├── VideoRepository.java
│ ├── CommentRepository.java
│ └── LikeRepository.java
│
├── entity/
│ ├── User.java
│ ├── Video.java
│ ├── Comment.java
│ └── Like.java
│
├── dto/
│ ├── LoginRequest.java
│ ├── RegisterRequest.java
│ ├── VideoResponse.java
│ └── ...
│
├── security/
│ ├── JwtFilter.java
│ ├── JwtService.java
│ ├── SecurityConfig.java
│ └── CustomUserDetailsService.java

---

# Database

PostgreSQL

Tables

- users
- videos
- comments
- likes

---

# API Endpoints

## Authentication

POST /api/users/register

POST /api/users/login

---

## Videos

GET /api/videos

GET /api/videos/{id}

POST /api/videos/upload

PUT /api/videos/{id}

POST /api/videos/like/{id}

PUT /api/videos/{id}/dislike

---

## Comments

GET /api/comments/{videoId}

POST /api/comments

---

## Likes

POST /api/likes

GET /api/likes/{videoId}

---

# Security

- JWT Authentication
- BCrypt Password Encryption
- Stateless Sessions
- Protected Upload APIs
- Owner verification before updating videos

---

# Future Improvements

- Delete Video
- My Channel
- Search Videos
- Subscribe System
- Notifications
- Watch History
- Playlist
- Watch Later
- Recommendations
- Cloud Storage (AWS S3 / Cloudinary)

---

