package com.kajal.backend.dto;
import com.kajal.backend.dto.VideoResponse;

public class VideoResponse {

    private Long id;
    private String title;
    private String description;
    private String videoUrl;
    private String thumbnailUrl;
    private String channelName;
    private String profileImage;


    public VideoResponse() {
    }

    public VideoResponse(Long id,
                         String title,
                         String description,
                         String videoUrl,
                         String thumbnailUrl,
                         String channelName,
                         String profileImage) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
        this.channelName = channelName;
        this.profileImage = profileImage;
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
    public String getChannelName() {
        return channelName;
    }

    public String getProfileImage() {
        return profileImage;
    }

}