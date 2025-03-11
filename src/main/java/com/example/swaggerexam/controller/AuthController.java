package com.example.swaggerexam.controller;

import com.example.swaggerexam.dto.ErrorResponseDto;
import com.example.swaggerexam.dto.LoginRequestDto;
import com.example.swaggerexam.dto.LoginResponseDto;
import com.example.swaggerexam.dto.RegisterRequestDto;
import com.example.swaggerexam.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인증 및 사용자 관리", description = "인증 관련 API")
@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private UserService userService;

  @Operation(summary = "회원가입", description = "이메일과 비밀번호를 입력하여 회원을 등록합니다.")
  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody RegisterRequestDto registerRequestDto) {
    String result = userService.register(registerRequestDto.getEmail(),
        registerRequestDto.getPassword());

    // 실패 시 400 에러 반환
    if ("Email already exists".equals(result)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request");
    }

    // 성공 시
    return ResponseEntity.status(HttpStatus.OK).body("ok");
  }

  @Operation(summary = "로그인", description = "이메일과 비밀번호를 입력하여 JWT 토큰을 발급받습니다.")
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
    String token = userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
    return token != null ? ResponseEntity.ok(new LoginResponseDto(token))
        : ResponseEntity.status(401).body(new ErrorResponseDto("Invalid credentials"));
  }

  @Operation(summary = "로그아웃", description = "JWT 토큰을 무효화하여 로그아웃합니다.")
  @PostMapping("/logout")
  public ResponseEntity<String> logout(
      @Parameter(description = "JWT 토큰", required = true, example = "Bearer JWT_ACCESS_TOKEN")
      @RequestHeader("Authorization") String token) {
    String jwtToken = token.substring(7);
    userService.logout(jwtToken);
    return ResponseEntity.ok("Logged out successfully");
  }
}