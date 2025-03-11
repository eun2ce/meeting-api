package com.example.swaggerexam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "모임 생성 요청 DTO")
@Getter
@Setter
public class CreateMeetingRequestDto {

  @Schema(description = "모임 이름", example = "Developers Meeting", requiredMode = Schema.RequiredMode.REQUIRED)
  private String name;

  @Schema(description = "모임 설명", example = "Weekly coding discussion", requiredMode = Schema.RequiredMode.REQUIRED)
  private String description;

  @Schema(description = "최대 참가 인원", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
  private int maxParticipants;
}
