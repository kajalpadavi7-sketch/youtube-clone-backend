package com.kajal.backend.dto;

public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private String channelName;
    private String profileImage;


    public UserResponse() {
    }


    public UserResponse(
            Long id,
            String name,
            String email,
            String channelName,
            String profileImage) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.channelName = channelName;
        this.profileImage = profileImage;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }


    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}