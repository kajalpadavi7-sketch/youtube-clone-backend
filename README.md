# 🎬 YouTube Clone - Backend

A RESTful backend for a YouTube Clone application built with **Java Spring Boot**.

The backend provides user authentication, JWT authorization, profile image management, video uploading and streaming, thumbnails, likes, comments, view counting, and channel information.

---

# 📌 Project Overview

This backend provides the REST APIs used by the YouTube Clone frontend.

It is responsible for:

- User registration and login
- JWT authentication
- User profile management
- Profile image upload
- Video upload
- Thumbnail upload
- Video streaming
- Video metadata management
- View counting
- Likes and dislikes
- Comments
- Channel information
- PostgreSQL database operations
- Owner verification for protected operations

---

# 🚀 Features

## 🔐 Authentication

- User Registration
- User Login
- BCrypt Password Encryption
- JWT Authentication
- Spring Security
- Stateless Authentication
- Protected APIs
- Current logged-in user information

---

## 👤 User & Profile

- User registration
- User login
- JWT-based authentication
- Get current logged-in user
- Update user information
- Upload profile image
- Store profile image path
- Display profile image across the frontend
- Channel/user information for videos and comments

---

## 🎥 Video Management

- Upload videos
- Upload video thumbnails
- Store video metadata in PostgreSQL
- Stream videos
- Get all videos
- Get video by ID
- Update video title
- Update video description
- Update video thumbnail
- Automatically count video views
- Verify video ownership before editing

---

## 👍 Engagement

- Like videos
- Dislike videos
- View like information
- Add comments
- Get comments for a video
- Display commenter profile information

---

## 🖼️ File Upload

The backend supports local file storage for:

- Profile images
- Video thumbnails
- Videos

Uploaded files are stored locally during development.

```
🛠️ Tech Stack
Java 21
Spring Boot 3
Spring Security
JWT
Spring Data JPA
Hibernate
PostgreSQL
Maven


📂 Project Structure
youtube-clone-backend/
│
├── src/
│   │
│   ├── main/
│   │   │
│   │   ├── java/
│   │   │   │
│   │   │   └── com/
│   │   │       └── kajal/
│   │   │           └── backend/
│   │   │
│   │   │               ├── config/
│   │   │               │   ├── PasswordConfig.java
│   │   │               │   └── WebConfig.java
│   │   │               │
│   │   │               ├── controller/
│   │   │               │   ├── CommentController.java
│   │   │               │   ├── HomeController.java
│   │   │               │   ├── LikeController.java
│   │   │               │   ├── UserController.java
│   │   │               │   └── VideoController.java
│   │   │               │
│   │   │               ├── dto/
│   │   │               │   ├── CommentRequest.java
│   │   │               │   ├── CommentResponse.java
│   │   │               │   ├── LikeRequest.java
│   │   │               │   ├── LikeResponse.java
│   │   │               │   ├── LoginRequest.java
│   │   │               │   ├── LoginResponse.java
│   │   │               │   ├── RegisterRequest.java
│   │   │               │   ├── UpdateVideoRequest.java
│   │   │               │   ├── UserResponse.java
│   │   │               │   ├── VideoRequest.java
│   │   │               │   └── VideoResponse.java
│   │   │               │
│   │   │               ├── entity/
│   │   │               │   ├── Comment.java
│   │   │               │   ├── User.java
│   │   │               │   ├── Video.java
│   │   │               │   └── VideoLike.java
│   │   │               │
│   │   │               ├── exception/
│   │   │               │   └── ...
│   │   │               │
│   │   │               ├── repository/
│   │   │               │   ├── CommentRepository.java
│   │   │               │   ├── UserRepository.java
│   │   │               │   ├── VideoLikeRepository.java
│   │   │               │   └── VideoRepository.java
│   │   │               │
│   │   │               ├── security/
│   │   │               │   ├── CustomUserDetailsService.java
│   │   │               │   ├── JwtFilter.java
│   │   │               │   ├── JwtService.java
│   │   │               │   └── SecurityConfig.java
│   │   │               │
│   │   │               ├── service/
│   │   │               │   ├── CommentService.java
│   │   │               │   ├── LikeService.java
│   │   │               │   ├── UserService.java
│   │   │               │   └── VideoService.java
│   │   │               │
│   │   │               └── util/
│   │   │                   └── BackendApplication.java
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── kajal/
│                   └── backend/
│                       └── BackendApplicationTests.java
│
├── upload/
│   ├── profiles/
│   ├── thumbnails/
│   └── videos/
│
├── target/
│
├── .gitignore
├── Dockerfile
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md