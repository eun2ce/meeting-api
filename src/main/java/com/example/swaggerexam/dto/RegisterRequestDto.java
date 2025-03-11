package com.example.swaggerexam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "회원가입 요청 DTO")
@Getter
@Setter
public class RegisterRequestDto {

  @Schema(description = "사용자 이메일", example = "user@example.com")
  private String email;

  @Schema(description = "사용자 비밀번호", example = "password123")
  private String password;
}
