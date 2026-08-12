package com.kajal.backend.controller;

import com.kajal.backend.dto.LoginRequest;
import com.kajal.backend.dto.RegisterRequest;
import com.kajal.backend.dto.UserResponse;
import com.kajal.backend.dto.LoginResponse;
import com.kajal.backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://192.168.1.12:5173",
        "https://youtube-clone-frontend-sigma-liard.vercel.app"
})
public class UserController {

    @Autowired
    private UserService userService;


    // ================================
    // LOGIN
    // ================================

    @PostMapping("/login")
    public LoginResponse loginUser(
            @RequestBody LoginRequest request) {

        System.out.println("LOGIN API HIT");

        return userService.loginUser(request);
    }


    // ================================
    // REGISTER
    // ================================

    @PostMapping("/register")
    public UserResponse registerUser(
            @RequestBody RegisterRequest request) {

        System.out.println("REGISTER API HIT");

        return userService.registerUser(request);
    }


    // ================================
    // UPLOAD PROFILE IMAGE
    // ================================

    @PutMapping(
            value = "/profile-image",
            consumes = "multipart/form-data"
    )
    public UserResponse updateProfileImage(
            @RequestParam("profileImage") MultipartFile profileImage)
            throws IOException {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userService.updateProfileImage(
                email,
                profileImage
        );
    }


    // ================================
    // GET MY PROFILE
    // ================================

    @GetMapping("/me")
    public UserResponse getMyProfile() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userService.getProfileByEmail(email);
    }


    // ================================
    // GET OTHER USER PROFILE
    // ================================

    @GetMapping("/{id}")
    public UserResponse getUserProfile(
            @PathVariable Long id) {

        return userService.getProfileById(id);
    }
}