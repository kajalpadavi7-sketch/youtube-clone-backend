package com.kajal.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kajal.backend.entity.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {

}