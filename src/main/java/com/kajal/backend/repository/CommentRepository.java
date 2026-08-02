package com.kajal.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kajal.backend.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long>{

    List<Comment> findByVideoIdOrderByCreatedAtDesc(Long videoId);

}