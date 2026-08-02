package com.kajal.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.kajal.backend.dto.LikeRequest;
import com.kajal.backend.dto.LikeResponse;
import com.kajal.backend.service.LikeService;

@RestController
@RequestMapping("/api/likes")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://youtube-clone-frontend-sigma-liard.vercel.app"
})
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping
    public String react(@RequestBody LikeRequest request){

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        likeService.react(request,email);

        return "Reaction Saved";

    }

    @GetMapping("/{videoId}")
    public LikeResponse getCounts(@PathVariable Long videoId){

        return likeService.getCounts(videoId);

    }

}