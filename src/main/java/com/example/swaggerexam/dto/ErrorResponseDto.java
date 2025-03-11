package com.example.swaggerexam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "Error 응답 DTO")
@Getter
@Setter
public class ErrorResponseDto {

  @Schema(description = "에러 메시지", example = "요청한 리소스를 찾을 수 없습니다.")
  private String error;

  public ErrorResponseDto(String error) {
    this.error = error;
  }
}