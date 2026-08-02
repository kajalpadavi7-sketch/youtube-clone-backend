package com.kajal.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kajal.backend.entity.VideoLike;

public interface VideoLikeRepository extends JpaRepository<VideoLike,Long>{

    Optional<VideoLike> findByUserIdAndVideoId(Long userId,Long videoId);

    long countByVideoIdAndLikedTrue(Long videoId);

    long countByVideoIdAndLikedFalse(Long videoId);

}