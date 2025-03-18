package com.example.swaggerexam.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.swaggerexam.entity.User;
import com.example.swaggerexam.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  @Test
  void testRegister_NewUser() {
    // Given
    String email = "test@example.com";
    String password = "password123";

    when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

    // When
    String result = userService.register(email, password);

    // Then
    verify(userRepository, times(1)).save(any(User.class));
    assertEquals("User registered successfully", result);
  }

  @Test
  void testRegister_ExistingUser() {
    // Given
    String email = "test@example.com";
    String password = "password123";
    User existingUser = new User();
    existingUser.setEmail(email);
    existingUser.setPassword(password);

    when(userRepository.findByEmail(email)).thenReturn(Optional.of(existingUser));

    // When
    String result = userService.register(email, password);

    // Then
    verify(userRepository, never()).save(any(User.class)); // save 호출되지 않았는지 확인
    assertEquals("Email already exists", result);
  }
}
