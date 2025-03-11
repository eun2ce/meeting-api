package com.example.swaggerexam.service;

import com.example.swaggerexam.entity.User;
import com.example.swaggerexam.repository.UserRepository;
import com.example.swaggerexam.util.JwtUtil;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;

  public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
    this.userRepository = userRepository;
    this.jwtUtil = jwtUtil;
  }

  public String register(String email, String password) {
    if (userRepository.findByEmail(email).isPresent()) {
      return "Email already exists";
    }
    User user = new User();
    user.setEmail(email);
    user.setPassword(password);
    userRepository.save(user);
    return "User registered successfully";
  }

  public String login(String email, String password) {
    Optional<User> user = userRepository.findByEmail(email);
    if (user.isPresent() && password.equals(user.get().getPassword())) {
      return jwtUtil.generateToken(user.get().getId());
    }
    return null;
  }

  public void logout(String token) {
    jwtUtil.invalidateToken(token);
  }
}