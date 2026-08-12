package com.kajal.backend.service;
import com.kajal.backend.dto.UpdateVideoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kajal.backend.entity.User;
import com.kajal.backend.entity.Video;
import com.kajal.backend.repository.UserRepository;
import com.kajal.backend.repository.VideoRepository;
import jakarta.servlet.http.HttpServletRequest;

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
        MultipartFile thumbnail,
        HttpServletRequest request) throws IOException {

        // --- FIXED PATH LOGIC ---
        // String baseDir = System.getProperty("user.dir");
        
        // String videoFolder = baseDir + File.separator + "upload" + File.separator + "videos" + File.separator;
        // String thumbnailFolder = baseDir + File.separator + "upload" + File.separator + "thumbnails" + File.separator;

        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

        String videoUrl = baseUrl + "/videos/" + video.getOriginalFilename();
        String thumbnailUrl = baseUrl + "/thumbnails/" + thumbnail.getOriginalFilename();

        String baseDir = System.getProperty("user.dir");

String videoFolder = baseDir
        + File.separator
        + "upload"
        + File.separator
        + "videos"
        + File.separator;

String thumbnailFolder = baseDir
        + File.separator
        + "upload"
        + File.separator
        + "thumbnails"
        + File.separator;

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
        // String baseUrl = "http://localhost:8080";

        // String videoUrl = baseUrl + "/videos/" + video.getOriginalFilename();
        // String thumbnailUrl = baseUrl + "/thumbnails/" + thumbnail.getOriginalFilename();
        
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

        videoData.setUser(user);
        videoData.setCreatedAt(LocalDateTime.now());

        videoData.setViews(0L);

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
                        video.getUser().getProfileImage(),
                        video.getViews(),
                        video.getCreatedAt()
                ))
                .collect(java.util.stream.Collectors.toList());
    }
    public VideoResponse getVideoById(Long id) {

    Video video = videoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Video not found"));
            if (video.getViews() == null) {
    video.setViews(0L);
}

video.setViews(video.getViews() + 1);

videoRepository.save(video);
            videoRepository.save(video);

    return new VideoResponse(
            video.getId(),
            video.getTitle(),
            video.getDescription(),
            video.getVideoUrl(),
            video.getThumbnailUrl(),
            video.getUser().getChannelName(),
            video.getUser().getProfileImage(),
            video.getViews(),
            video.getCreatedAt()
    );
}
public String updateVideo(
        Long id,
        String title,
        String description,
        MultipartFile thumbnail,
        String email,
        HttpServletRequest request
) throws IOException {

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

    Video video = videoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Video not found"));

    if (!video.getUser().getId().equals(user.getId())) {
        throw new RuntimeException("You can edit only your own video.");
    }

    video.setTitle(title);
    video.setDescription(description);

    if (thumbnail != null && !thumbnail.isEmpty()) {

        String folder = System.getProperty("user.dir")
                + File.separator
                + "upload"
                + File.separator
                + "thumbnails"
                + File.separator;

        File dir = new File(folder);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        Path target = Paths.get(folder)
                .resolve(thumbnail.getOriginalFilename());

        Files.copy(
                thumbnail.getInputStream(),
                target,
                StandardCopyOption.REPLACE_EXISTING
        );

        String baseUrl = request.getScheme()
        + "://"
        + request.getServerName()
        + ":"
        + request.getServerPort();

video.setThumbnailUrl(
        baseUrl
                + "/thumbnails/"
                + thumbnail.getOriginalFilename()
);
    }

    videoRepository.save(video);

    return "Video Updated Successfully";
}
}