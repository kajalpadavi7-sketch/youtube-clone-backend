package com.kajal.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.kajal.backend.dto.CommentRequest;
import com.kajal.backend.dto.CommentResponse;
import com.kajal.backend.service.CommentService;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = {
    "http://localhost:5173",
    "https://youtube-clone-frontend-sigma-liard.vercel.app"
})
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public String addComment(@RequestBody CommentRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        commentService.addComment(request, email);

        return "Comment Added Successfully";
    }

    @GetMapping("/video/{videoId}")
    public List<CommentResponse> getComments(
            @PathVariable Long videoId) {

        return commentService.getComments(videoId);
    }

}