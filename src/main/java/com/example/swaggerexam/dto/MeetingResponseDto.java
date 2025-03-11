package com.example.swaggerexam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "모임 응답 DTO")
@Getter
@Setter
public class MeetingResponseDto {

  @Schema(description = "모임 고유 ID", example = "1")
  private Long id;

  @Schema(description = "모임 이름", example = "Developers Meeting")
  private String name;

  @Schema(description = "모임 설명", example = "Weekly coding discussion")
  private String description;

  @Schema(description = "최대 참가 인원", example = "10")
  private int maxParticipants;

  @Schema(description = "현재 참가 인원", example = "5")
  private int currentParticipants;

  public MeetingResponseDto(Long id, String name, String description, int maxParticipants,
      int currentParticipants) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.maxParticipants = maxParticipants;
    this.currentParticipants = currentParticipants;
  }
}
