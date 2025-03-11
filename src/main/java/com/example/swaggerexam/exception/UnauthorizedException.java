package com.example.swaggerexam.exception;

public class UnauthorizedException extends RuntimeException {

  public UnauthorizedException(String message) {
    super(message);  // 예외 메시지를 상위 클래스에 전달
  }

  public UnauthorizedException(String message, Throwable cause) {
    super(message, cause);  // 예외 메시지와 원인(Throwable)을 상위 클래스에 전달
  }
}