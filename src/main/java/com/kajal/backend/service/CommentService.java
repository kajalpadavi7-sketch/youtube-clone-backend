package com.kajal.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kajal.backend.dto.CommentRequest;
import com.kajal.backend.dto.CommentResponse;
import com.kajal.backend.entity.Comment;
import com.kajal.backend.entity.User;
import com.kajal.backend.entity.Video;
import com.kajal.backend.repository.CommentRepository;
import com.kajal.backend.repository.UserRepository;
import com.kajal.backend.repository.VideoRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserRepository userRepository;


    public void addComment(CommentRequest request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Video video = videoRepository.findById(request.getVideoId())
                .orElseThrow(() -> new RuntimeException("Video not found"));

        Comment comment = new Comment();

        comment.setComment(request.getComment());
        comment.setUser(user);
        comment.setVideo(video);
        comment.setCreatedAt(LocalDateTime.now());

        commentRepository.save(comment);
    }


    public List<CommentResponse> getComments(Long videoId) {

        return commentRepository
                .findByVideoIdOrderByCreatedAtDesc(videoId)
                .stream()
                .map(comment -> new CommentResponse(
                        comment.getId(),
                        comment.getComment(),
                        comment.getUser().getId(),
                        comment.getUser().getChannelName(),
                        comment.getUser().getProfileImage(),
                        comment.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }
}