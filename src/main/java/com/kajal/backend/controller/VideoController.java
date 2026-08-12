package com.kajal.backend.controller;
import java.io.IOException;
import java.util.List;
import com.kajal.backend.entity.Video;
import com.kajal.backend.dto.UpdateVideoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import com.kajal.backend.service.VideoService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.kajal.backend.dto.VideoResponse;



@RestController
@RequestMapping("/api/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/test")
    public String test() {
    return "Video Controller Working";
    }

@GetMapping
public List<VideoResponse> getAllVideos() {
    return videoService.getAllVideos();
}

@GetMapping("/{id}")
public VideoResponse getVideoById(@PathVariable Long id) {
    return videoService.getVideoById(id);
}
    //@PostMapping("/upload")
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
        public String uploadVideo(
        @RequestParam("title") String title,
        @RequestParam("description") String description,
        @RequestParam("video") MultipartFile video,
       @RequestParam("thumbnail") MultipartFile thumbnail,
        HttpServletRequest request) throws IOException{     
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

    return videoService.uploadVideo(
    title,
    description,
    email,
    video,
    thumbnail,
    request

);
}
@PutMapping(value = "/{id}", consumes = "multipart/form-data")
public String updateVideo(

        @PathVariable Long id,

        @RequestParam String title,

        @RequestParam String description,

        @RequestParam(required = false) MultipartFile thumbnail,

        HttpServletRequest request

) throws IOException {

    Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();

    String email = authentication.getName();

    return videoService.updateVideo(
            id,
            title,
            description,
            thumbnail,
            email,
            request
    );
}
@PutMapping("/{id}/dislike")
public Video dislikeVideo(@PathVariable Long id) {
    return videoService.dislikeVideo(id);
}
@PostMapping("/like/{id}")
public Video likeVideo(@PathVariable Long id) {
    return videoService.likeVideo(id);
}
}