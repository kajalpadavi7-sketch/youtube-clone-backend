package com.kajal.backend.dto;

public class CommentRequest {

    private Long videoId;

    private String comment;

    public CommentRequest() {
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}