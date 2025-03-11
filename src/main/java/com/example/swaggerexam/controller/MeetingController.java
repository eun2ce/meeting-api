package com.example.swaggerexam.controller;

import com.example.swaggerexam.dto.CreateMeetingRequestDto;
import com.example.swaggerexam.dto.MeetingResponseDto;
import com.example.swaggerexam.dto.UpdateMeetingRequestDto;
import com.example.swaggerexam.exception.UnauthorizedException;
import com.example.swaggerexam.service.MeetingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meetings")
public class MeetingController {

  @Autowired
  private MeetingService meetingService;

  // Authorization 헤더에서 Bearer token을 추출하여 Service로 전달
  private String extractTokenFromHeader(String authorizationHeader) {
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      return authorizationHeader.substring(7);
    }
    throw new UnauthorizedException("Unauthorized access. Token is missing.");
  }

  @Operation(summary = "모임 생성", description = "새로운 모임을 생성합니다.")
  @ApiResponse(responseCode = "200", description = "모임이 성공적으로 생성되었습니다.")
  @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UnauthorizedException.class)))
  @PostMapping
  public ResponseEntity<MeetingResponseDto> createMeeting(
      @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
      @RequestBody CreateMeetingRequestDto createMeetingRequestDto) {
    String token = extractTokenFromHeader(authorizationHeader);
    MeetingResponseDto meetingResponse = meetingService.createMeeting(token,
        createMeetingRequestDto);
    if (meetingResponse == null) {
      throw new UnauthorizedException("Invalid or expired token");
    }
    return ResponseEntity.ok(meetingResponse);
  }

  @Operation(summary = "모임 목록 조회", description = "모든 모임의 목록을 조회합니다.")
  @ApiResponse(responseCode = "200", description = "모임 목록을 성공적으로 조회했습니다.")
  @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UnauthorizedException.class)))
  @GetMapping
  public ResponseEntity<List<MeetingResponseDto>> getAllMeetings(
      @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
    String token = extractTokenFromHeader(authorizationHeader);
    List<MeetingResponseDto> meetings = meetingService.getAllMeetings(token);
    if (meetings == null) {
      throw new UnauthorizedException("Invalid or expired token");
    }
    return ResponseEntity.ok(meetings);
  }

  @Operation(summary = "모임 수정", description = "모임을 수정합니다. (생성자만 가능)")
  @ApiResponse(responseCode = "200", description = "모임이 성공적으로 수정되었습니다.")
  @ApiResponse(responseCode = "401", description = "생성자가 아니거나 유효하지 않은 토큰", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UnauthorizedException.class)))
  @PutMapping("/{meetingId}")
  public ResponseEntity<MeetingResponseDto> updateMeeting(
      @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
      @PathVariable Long meetingId,
      @RequestBody UpdateMeetingRequestDto updateMeetingRequestDto) {
    String token = extractTokenFromHeader(authorizationHeader);
    MeetingResponseDto updatedMeeting = meetingService.updateMeeting(token, meetingId,
        updateMeetingRequestDto);
    if (updatedMeeting == null) {
      throw new UnauthorizedException("Unauthorized or invalid access");
    }
    return ResponseEntity.ok(updatedMeeting);
  }

  @Operation(summary = "모임 삭제", description = "모임을 삭제합니다. (생성자만 가능)")
  @ApiResponse(responseCode = "200", description = "모임이 성공적으로 삭제되었습니다.")
  @ApiResponse(responseCode = "401", description = "생성자가 아니거나 유효하지 않은 토큰", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UnauthorizedException.class)))
  @DeleteMapping("/{meetingId}")
  public ResponseEntity<String> deleteMeeting(
      @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
      @PathVariable Long meetingId) {
    String token = extractTokenFromHeader(authorizationHeader);
    meetingService.deleteMeeting(token, meetingId);
    return ResponseEntity.ok("모임이 삭제되었습니다.");
  }

  // UnauthorizedException 핸들링
  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException ex) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
  }
}
