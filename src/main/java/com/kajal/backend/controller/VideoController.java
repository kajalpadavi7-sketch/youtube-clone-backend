package com.kajal.backend.controller;
import java.io.IOException;
import java.util.List;
import com.kajal.backend.entity.Video;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.kajal.backend.service.VideoService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;



@RestController
@RequestMapping("/api/videos")
@CrossOrigin(origins = "http://localhost:5173")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/test")
    public String test() {
    return "Video Controller Working";
    }
    @GetMapping
public List<Video> getAllVideos() {
    return videoService.getAllVideos();
}
    //@PostMapping("/upload")
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
        public String uploadVideo(
        @RequestParam("title") String title,
        @RequestParam("description") String description,
        @RequestParam("video") MultipartFile video,
        @RequestParam("thumbnail") MultipartFile thumbnail) throws IOException{
            
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

    return videoService.uploadVideo(
        title,
        description,
        email,
        video,
        thumbnail
);
}
}
