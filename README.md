# 🎬 YouTube Clone - Backend

The backend engine of the YouTube Clone application built using **Java Spring Boot**, **Spring Security**, **PostgreSQL**, and **JWT Authentication**.

---

# 📌 Project Description

This module handles secure user authentication, robust video/thumbnail asset management, multi-part handling, database operations via Hibernate/JPA, and exposes REST APIs for the client application.

---

# 🚀 Features Completed

- **Authentication Module:**
  - User Registration & Login endpoints.
  - Password Encryption using `BCrypt`.
  - Stateless JSON Web Token (`JWT`) authentication flow.
  - Custom `JwtFilter` integrated before `UsernamePasswordAuthenticationFilter`.
  - Secure Spring Security configuration with tailored public/private API routes.
  - PostgreSQL user database management.

- **Video Asset Module:**
  - Video and Thumbnail dynamic multipart uploads.
  - Isolated server filesystem storage for streaming assets.
  - Automated database record indexing mapping exact URLs to media files.
  - Custom data layers via Video Entity, Repository, Service, and Controller blocks.
  - Video Feed Endpoint (`GET /api/videos`) with public cross-origin accessibility.

---

# 🛠 Tech Stack

- **Core Language:** Java 21
- **Framework:** Spring Boot 3.x
- **Security:** Spring Security & JWT (JsonWebToken)
- **Data Layers:** Spring Data JPA & Hibernate
- **Database Engine:** PostgreSQL
- **Build Automation:** Maven (using Maven Wrapper `./mvnw`)

---

# 📂 Folder Structure

```text
backend/
├── src/main/java/com/kajal/backend
│   ├── config/
│   │   ├── PasswordConfig.java
│   │   └── WebConfig.java
│   ├── controller/
│   │   ├── UserController.java
│   │   └── VideoController.java
│   ├── dto/
│   │   ├── LoginRequest.java
│   │   ├── LoginResponse.java
│   │   ├── RegisterRequest.java
│   │   ├── UserResponse.java
│   │   ├── VideoRequest.java
│   │   └── VideoResponse.java
│   ├── entity/
│   │   ├── User.java
│   │   └── Video.java
│   ├── repository/
│   │   ├── UserRepository.java
│   │   └── VideoRepository.java
│   ├── security/
│   │   ├── CustomUserDetailsService.java
│   │   ├── JwtFilter.java
│   │   ├── JwtService.java
│   │   └── SecurityConfig.java
│   ├── service/
│   │   ├── UserService.java
│   │   └── VideoService.java
├── resources/
│   └── application.properties
├── pom.xml
└── mvnw