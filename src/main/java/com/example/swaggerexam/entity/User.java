package com.example.swaggerexam.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "사용자 정보를 담고 있는 엔티티")
@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "사용자 고유 ID", example = "1")
  private Long id;

  @Column(unique = true, nullable = false)
  @Schema(description = "사용자 이메일", example = "john.doe@example.com")
  private String email;

  @Column(nullable = false)
  @Schema(description = "사용자 비밀번호", example = "password123")
  private String password;
}