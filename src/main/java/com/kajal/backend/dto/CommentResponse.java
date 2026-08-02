package com.kajal.backend.dto;

import java.time.LocalDateTime;

public class CommentResponse {

    private Long id;
    private String comment;
    private String userName;
    private String profileImage;
    private LocalDateTime createdAt;

    public CommentResponse(
            Long id,
            String comment,
            String userName,
            String profileImage,
            LocalDateTime createdAt) {

        this.id = id;
        this.comment = comment;
        this.userName = userName;
        this.profileImage = profileImage;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public String getUserName() {
        return userName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}