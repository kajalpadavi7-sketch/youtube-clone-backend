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
import com.kajal.backend.security.JwtService;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

        @Autowired
        private JwtService jwtService;

    public User registerUser(RegisterRequest request) {

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

        return userRepository.save(user);
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
}