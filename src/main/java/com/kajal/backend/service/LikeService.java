package com.kajal.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kajal.backend.dto.LikeRequest;
import com.kajal.backend.dto.LikeResponse;
import com.kajal.backend.entity.User;
import com.kajal.backend.entity.Video;
import com.kajal.backend.entity.VideoLike;
import com.kajal.backend.repository.UserRepository;
import com.kajal.backend.repository.VideoLikeRepository;
import com.kajal.backend.repository.VideoRepository;

@Service
public class LikeService {

    @Autowired
    private VideoLikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;

    public void react(LikeRequest request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Video video = videoRepository.findById(request.getVideoId())
                .orElseThrow(() -> new RuntimeException("Video not found"));

        Optional<VideoLike> optional =
                likeRepository.findByUserIdAndVideoId(
                        user.getId(),
                        video.getId());

        VideoLike like;

        if(optional.isPresent()){

            like = optional.get();

        }else{

            like = new VideoLike();

            like.setUser(user);

            like.setVideo(video);

        }

        like.setLiked(request.isLiked());

        likeRepository.save(like);

    }

    public LikeResponse getCounts(Long videoId){

        long likes =
                likeRepository.countByVideoIdAndLikedTrue(videoId);

        long dislikes =
                likeRepository.countByVideoIdAndLikedFalse(videoId);

        return new LikeResponse(likes,dislikes);

    }

}