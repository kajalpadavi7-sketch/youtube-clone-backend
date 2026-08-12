package com.kajal.backend.service;
import java.util.Optional;
import com.kajal.backend.dto.LoginRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.kajal.backend.dto.LoginResponse;
import com.kajal.backend.security.JwtService;
import com.kajal.backend.dto.RegisterRequest;
import com.kajal.backend.entity.User;
import com.kajal.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kajal.backend.dto.UserResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

import com.kajal.backend.dto.UserResponse;


@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

        @Autowired
        private JwtService jwtService;

    public UserResponse registerUser(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        //user.setPassword(passwordEncoder.encode(request.getPassword()));
        String encryptedPassword = passwordEncoder.encode(request.getPassword());

        System.out.println("Original Password : " + request.getPassword());
        System.out.println("Encrypted Password: " + encryptedPassword);

        user.setPassword(encryptedPassword);

        user.setChannelName(request.getChannelName());

        User savedUser = userRepository.save(user);

return new UserResponse(
        savedUser.getId(),
        savedUser.getName(),
        savedUser.getEmail(),
        savedUser.getChannelName(),
        savedUser.getProfileImage()
);
    }
    
//     public LoginResponse loginUser(LoginRequest request)
//     {

//     Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

//     if (optionalUser.isEmpty()) {
//         throw new RuntimeException("User not found");
//     }

//     User user = optionalUser.get();

//     if (!passwordEncoder.matches(
//         request.getPassword(),
//         user.getPassword())) {

//     throw new RuntimeException("Invalid password");
// }
//     String token = jwtService.generateToken(user.getEmail());

//     return new LoginResponse(token);
// }
public LoginResponse loginUser(LoginRequest request)
{
    System.out.println("Email from request = " + request.getEmail());

    Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

    System.out.println("User found = " + optionalUser.isPresent());

    if (optionalUser.isEmpty()) {
        throw new RuntimeException("User not found");
    }

    User user = optionalUser.get();

    if (!passwordEncoder.matches(
            request.getPassword(),
            user.getPassword())) {

        throw new RuntimeException("Invalid password");
    }

    String token = jwtService.generateToken(user.getEmail());

    return new LoginResponse(token);
}

// ========================================
// UPDATE PROFILE IMAGE
// ========================================

public UserResponse updateProfileImage(
        String email,
        MultipartFile profileImage) throws IOException {

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new RuntimeException("User not found"));


    if (profileImage == null || profileImage.isEmpty()) {
        throw new RuntimeException("Profile image is required");
    }


    String baseDir = System.getProperty("user.dir");

    String profileFolder =
            baseDir
            + File.separator
            + "upload"
            + File.separator
            + "profiles"
            + File.separator;


    File directory = new File(profileFolder);

    if (!directory.exists()) {
        directory.mkdirs();
    }


    String originalFilename =
            profileImage.getOriginalFilename();

    String extension = "";

    if (originalFilename != null &&
            originalFilename.contains(".")) {

        extension =
                originalFilename.substring(
                        originalFilename.lastIndexOf(".")
                );
    }


    String filename =
            "profile_" + user.getId() + extension;


    Path targetPath =
            Paths.get(profileFolder)
                    .resolve(filename);


    Files.copy(
            profileImage.getInputStream(),
            targetPath,
            StandardCopyOption.REPLACE_EXISTING
    );


    // Save URL in database

    String profileUrl =
            "/profiles/" + filename;


    user.setProfileImage(profileUrl);

    User savedUser =
            userRepository.save(user);


    return new UserResponse(
    savedUser.getId(),
    savedUser.getName(),
    savedUser.getEmail(),
    savedUser.getChannelName(),
    savedUser.getProfileImage()
);
}
// ========================================
// GET MY PROFILE
// ========================================

public UserResponse getProfileByEmail(String email) {

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new RuntimeException("User not found"));

    return convertToUserResponse(user);
}


// ========================================
// GET USER PROFILE BY ID
// ========================================

public UserResponse getProfileById(Long id) {

    User user = userRepository.findById(id)
            .orElseThrow(() ->
                    new RuntimeException("User not found"));

    return convertToUserResponse(user);
}


// ========================================
// CONVERT USER -> RESPONSE
// ========================================

private UserResponse convertToUserResponse(User user) {

    return new UserResponse(
    user.getId(),
    user.getName(),
    user.getEmail(),
    user.getChannelName(),
    user.getProfileImage()
);
}

}