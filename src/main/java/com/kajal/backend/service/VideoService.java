package com.kajal.backend.service;
import com.kajal.backend.dto.UpdateVideoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;
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
    private Set<String> extractWords(String text) {

    if (text == null || text.isBlank()) {
        return Collections.emptySet();
    }

    Set<String> stopWords = Set.of(
            "the",
            "a",
            "an",
            "is",
            "are",
            "and",
            "or",
            "to",
            "of",
            "in",
            "on",
            "for",
            "with",
            "this",
            "that",
            "video",
            "new"
    );

    return Arrays.stream(
                    text.toLowerCase()
                            .replaceAll("[^a-zA-Z0-9 ]", " ")
                            .split("\\s+")
            )
            .filter(word -> word.length() > 2)
            .filter(word -> !stopWords.contains(word))
            .collect(Collectors.toSet());
}
 public Video dislikeVideo(Long id) {

    Video video = videoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Video not found"));

    video.setDislikes(video.getDislikes() + 1);

    return videoRepository.save(video);
}
@Transactional(readOnly = true)
public List<VideoResponse> getRecommendedVideos(Long currentVideoId) {

    // Current video
    Video currentVideo = videoRepository.findById(currentVideoId)
            .orElseThrow(() -> new RuntimeException("Video not found"));

    // Current video's title + description
    Set<String> currentWords = extractWords(
            currentVideo.getTitle() + " " +
            (currentVideo.getDescription() == null
                    ? ""
                    : currentVideo.getDescription())
    );

    Long currentUserId = currentVideo.getUser().getId();

    // Get all videos
    List<Video> allVideos = videoRepository.findAll();

    return allVideos.stream()

            // Current video ko recommendation mein mat dikhana
            .filter(video -> !video.getId().equals(currentVideoId))

            // Har video ka recommendation score calculate karo
            .map(video -> {

                Set<String> videoWords = extractWords(
                        video.getTitle() + " " +
                        (video.getDescription() == null
                                ? ""
                                : video.getDescription())
                );

                // Common words
                long commonWords = videoWords.stream()
                        .filter(currentWords::contains)
                        .count();

                double score = 0;

                // 1. Similar title/description
                score += commonWords * 10;

                // 2. Same channel
                if (video.getUser().getId().equals(currentUserId)) {
                    score += 20;
                }

                // 3. Views
                if (video.getViews() != null) {
                    score += Math.log10(video.getViews() + 1) * 2;
                }

                // 4. Recent videos ko small bonus
                if (video.getCreatedAt() != null) {
                    long daysOld = java.time.Duration.between(
                            video.getCreatedAt(),
                            LocalDateTime.now()
                    ).toDays();

                    if (daysOld <= 7) {
                        score += 5;
                    } else if (daysOld <= 30) {
                        score += 3;
                    }
                }

                return new RecommendationResult(video, score);
            })

            // Highest score first
            .sorted(
                    Comparator.comparingDouble(
                            RecommendationResult::score
                    ).reversed()
            )

            // ONLY 8 recommendations
            .limit(8)

            // Convert Video -> VideoResponse
            .map(result -> {

                Video video = result.video();

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
            })

            .collect(Collectors.toList());
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
private record RecommendationResult(
        Video video,
        double score
) {
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