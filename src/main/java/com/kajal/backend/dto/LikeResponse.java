package com.kajal.backend.dto;

public class LikeResponse {

    private long likes;

    private long dislikes;

    public LikeResponse(long likes,long dislikes){

        this.likes=likes;

        this.dislikes=dislikes;

    }

    public long getLikes() {
        return likes;
    }

    public long getDislikes() {
        return dislikes;
    }

}