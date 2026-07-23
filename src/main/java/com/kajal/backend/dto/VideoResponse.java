package com.kajal.backend.dto;

public class VideoResponse {

    private Long id;
    private String title;
    private String description;
    private String videoUrl;
    private String thumbnailUrl;

    public VideoResponse() {
    }

    public VideoResponse(Long id,
                         String title,
                         String description,
                         String videoUrl,
                         String thumbnailUrl) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}