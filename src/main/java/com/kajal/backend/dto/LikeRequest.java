package com.kajal.backend.dto;

public class LikeRequest {

    private Long videoId;

    private boolean liked;

    public LikeRequest(){}

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

}