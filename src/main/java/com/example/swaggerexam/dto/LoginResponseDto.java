package com.example.swaggerexam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "로그인 응답 DTO")
@Getter
@Setter
public class LoginResponseDto {

  @Schema(description = "사용자 인증을 위한 JWT 토큰", example = "bearer eyJhb..")
  private String token;

  public LoginResponseDto(String token) {
    this.token = token;
  }
}
