package com.kajal.backend.controller;
import com.kajal.backend.dto.LoginRequest;
import com.kajal.backend.dto.RegisterRequest;
import com.kajal.backend.entity.User;
import com.kajal.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.kajal.backend.dto.UserResponse;
import com.kajal.backend.dto.LoginResponse;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody RegisterRequest request) {
        return userService.registerUser(request);
    }
    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody LoginRequest request)
    {
    return userService.loginUser(request);
}
}