package com.kajal.backend.dto;

import java.time.LocalDateTime;

public class CommentResponse {

    private Long id;
    private String comment;

    private Long userId;
    private String userName;
    private String profileImage;

    private LocalDateTime createdAt;


    public CommentResponse() {
    }


    public CommentResponse(
            Long id,
            String comment,
            Long userId,
            String userName,
            String profileImage,
            LocalDateTime createdAt) {

        this.id = id;
        this.comment = comment;
        this.userId = userId;
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

    public Long getUserId() {
        return userId;
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