package com.kajal.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kajal.backend.entity.User;
import com.kajal.backend.entity.Video;
import com.kajal.backend.repository.UserRepository;
import com.kajal.backend.repository.VideoRepository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${file.upload.video}")
    private String videoFolder;

    @Value("${file.upload.thumbnail}")
    private String thumbnailFolder;

    public String uploadVideo(
            String title,
            String description,
            String email,
            MultipartFile video,
            MultipartFile thumbnail) throws IOException {

        // Create folders if not exists
        new File(videoFolder).mkdirs();
        new File(thumbnailFolder).mkdirs();

        String videoPath = videoFolder + video.getOriginalFilename();
String thumbnailPath = thumbnailFolder + thumbnail.getOriginalFilename();

// Save files on disk
video.transferTo(new File(videoPath));
thumbnail.transferTo(new File(thumbnailPath));

// Save URLs in database
String videoUrl = "/videos/" + video.getOriginalFilename();
String thumbnailUrl = "/thumbnails/" + thumbnail.getOriginalFilename();

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

        videoData.setUploadedBy(user.getId());
        videoData.setCreatedAt(LocalDateTime.now());

        videoRepository.save(videoData);

        System.out.println("Video Saved At : " + videoPath);
        System.out.println("Thumbnail Saved At : " + thumbnailPath);

        return "Files Saved Successfully";
    }

    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }
}