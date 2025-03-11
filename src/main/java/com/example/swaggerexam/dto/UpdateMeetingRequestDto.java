package com.example.swaggerexam.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "모임 수정 요청 DTO")
@Getter
@Setter
public class UpdateMeetingRequestDto {

  @Schema(description = "모임 이름", example = "Updated Meeting Name", requiredMode = Schema.RequiredMode.REQUIRED)
  private String name;

  @Schema(description = "모임 설명", example = "Updated description", requiredMode = Schema.RequiredMode.REQUIRED)
  private String description;

  @Schema(description = "최대 참가 인원", example = "15", requiredMode = Schema.RequiredMode.REQUIRED)
  private int maxParticipants;
}
