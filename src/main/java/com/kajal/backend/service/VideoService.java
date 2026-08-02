package com.kajal.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kajal.backend.entity.User;
import com.kajal.backend.entity.Video;
import com.kajal.backend.repository.UserRepository;
import com.kajal.backend.repository.VideoRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import com.kajal.backend.dto.VideoResponse;



@Service
public class VideoService {

    @Autowired
    
private VideoRepository videoRepository;

    @Autowired
    private UserRepository userRepository;


    public String uploadVideo(
            String title,
            String description,
            String email,
            MultipartFile video,
            MultipartFile thumbnail) throws IOException {

        // --- FIXED PATH LOGIC ---
        String baseDir = System.getProperty("user.dir");
        
        String videoFolder = baseDir + File.separator + "upload" + File.separator + "videos" + File.separator;
        String thumbnailFolder = baseDir + File.separator + "upload" + File.separator + "thumbnails" + File.separator;

        // Create folders if they do not exist
        File vDir = new File(videoFolder);
        File tDir = new File(thumbnailFolder);
        if (!vDir.exists()) vDir.mkdirs();
        if (!tDir.exists()) tDir.mkdirs();

        // Convert String paths to java.nio.file.Path objects
        Path targetVideoPath = Paths.get(videoFolder).resolve(video.getOriginalFilename());
        Path targetThumbnailPath = Paths.get(thumbnailFolder).resolve(thumbnail.getOriginalFilename());

        // --- SECURE STORAGE LOGIC ---
        // transferTo() ki jagah Files.copy use karein (Tomcat restriction bypass karne ke liye)
        Files.copy(video.getInputStream(), targetVideoPath, StandardCopyOption.REPLACE_EXISTING);
        Files.copy(thumbnail.getInputStream(), targetThumbnailPath, StandardCopyOption.REPLACE_EXISTING);

        // Save URLs in database
        String baseUrl = "http://localhost:8080";

        String videoUrl = baseUrl + "/videos/" + video.getOriginalFilename();
        String thumbnailUrl = baseUrl + "/thumbnails/" + thumbnail.getOriginalFilename();
        
        // Find logged in user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Save video details
        Video videoData = new Video();

        videoData.setTitle(title);
        videoData.setDescription(description);

        // Save URL instead of local path
        videoData.setVideoUrl(videoUrl);
        videoData.setThumbnailUrl(thumbnailUrl);

      //  videoData.setUploadedBy(user.getId());
        videoData.setUser(user);
        videoData.setCreatedAt(LocalDateTime.now());

        videoRepository.save(videoData);

        System.out.println("Video Saved At : " + targetVideoPath.toString());
        System.out.println("Thumbnail Saved At : " + targetThumbnailPath.toString());

        return "Files Saved Successfully";
    }
 public Video dislikeVideo(Long id) {

    Video video = videoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Video not found"));

    video.setDislikes(video.getDislikes() + 1);

    return videoRepository.save(video);
}
public Video likeVideo(Long id) {

    Video video = videoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Video not found"));

    video.setLikes(video.getLikes() + 1);

    return videoRepository.save(video);
}

    public List<VideoResponse> getAllVideos() {
        return videoRepository.findAll().stream()
                .map(video -> new VideoResponse(
                        video.getId(),
                        video.getTitle(),
                        video.getDescription(),
                        video.getVideoUrl(),
                        video.getThumbnailUrl(),
                        video.getUser().getChannelName(),
                        video.getUser().getProfileImage()
                ))
                .collect(java.util.stream.Collectors.toList());
    }
    public VideoResponse getVideoById(Long id) {

    Video video = videoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Video not found"));

    return new VideoResponse(
            video.getId(),
            video.getTitle(),
            video.getDescription(),
            video.getVideoUrl(),
            video.getThumbnailUrl(),
            video.getUser().getChannelName(),
            video.getUser().getProfileImage()
    );
}
}