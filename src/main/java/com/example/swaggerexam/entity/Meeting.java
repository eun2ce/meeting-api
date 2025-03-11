package com.example.swaggerexam.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "모임 정보를 담고 있는 엔티티")
@Getter
@Setter
@Entity
public class Meeting {

  @Schema(description = "모임 고유 ID", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Schema(description = "모임 이름", example = "Developers Meeting")
  private String name;

  @Schema(description = "모임 설명", example = "Weekly coding discussion")
  private String description;

  @Schema(description = "최대 참가 인원", example = "10")
  private int maxParticipants;

  @Schema(description = "현재 참가 인원", example = "5")
  private int currentParticipants;

  @ManyToOne
  @JoinColumn(name = "creator_id")
  @Schema(description = "모임을 생성한 사용자")
  private User creator;
}
