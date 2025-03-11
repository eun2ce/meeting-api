package com.example.swaggerexam.service;

import com.example.swaggerexam.dto.CreateMeetingRequestDto;
import com.example.swaggerexam.dto.MeetingResponseDto;
import com.example.swaggerexam.dto.UpdateMeetingRequestDto;
import com.example.swaggerexam.entity.Meeting;
import com.example.swaggerexam.entity.User;
import com.example.swaggerexam.repository.MeetingRepository;
import com.example.swaggerexam.repository.UserRepository;
import com.example.swaggerexam.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MeetingService {

  private final MeetingRepository meetingRepository;
  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;

  public MeetingService(MeetingRepository meetingRepository, UserRepository userRepository,
      JwtUtil jwtUtil) {
    this.meetingRepository = meetingRepository;
    this.userRepository = userRepository;
    this.jwtUtil = jwtUtil;
  }

  // JWT 토큰에서 사용자 정보 추출 (로그인된 사용자만 가능)
  @Operation(summary = "현재 사용자 조회", description = "JWT 토큰을 통해 현재 사용자의 정보를 조회합니다.")
  public Optional<User> getCurrentUser(String token) {
    Long userId = jwtUtil.validateToken(token);
    if (userId == null) {
      return Optional.empty();  // 유효하지 않은 토큰
    }
    Optional<User> user = userRepository.findById(userId);
    return user;
  }

  @Operation(summary = "모임 생성", description = "새로운 모임을 생성합니다.")
  @ApiResponse(responseCode = "201", description = "모임이 성공적으로 생성되었습니다.")
  @Transactional
  public MeetingResponseDto createMeeting(String token,
      CreateMeetingRequestDto createMeetingRequestDto) {
    Optional<User> creatorOpt = getCurrentUser(token);
    if (creatorOpt.isEmpty()) {
      return null;  // 유효하지 않은 토큰
    }

    User creator = creatorOpt.get();
    Meeting meeting = new Meeting();
    meeting.setName(createMeetingRequestDto.getName());
    meeting.setDescription(createMeetingRequestDto.getDescription());
    meeting.setMaxParticipants(createMeetingRequestDto.getMaxParticipants());
    meeting.setCurrentParticipants(0);
    meeting.setCreator(creator);

    meetingRepository.save(meeting);

    return new MeetingResponseDto(meeting.getId(), meeting.getName(), meeting.getDescription(),
        meeting.getMaxParticipants(), meeting.getCurrentParticipants());
  }

  @Operation(summary = "모임 목록 조회", description = "모든 모임의 목록을 조회합니다.")
  @ApiResponse(responseCode = "200", description = "모임 목록을 성공적으로 조회했습니다.")
  public List<MeetingResponseDto> getAllMeetings(String token) {
    Optional<User> userOpt = getCurrentUser(token);
    if (userOpt.isEmpty()) {
      return null;  // 유효하지 않은 토큰
    }

    List<Meeting> meetings = meetingRepository.findAll();
    return meetings.stream()
        .map(meeting -> new MeetingResponseDto(meeting.getId(), meeting.getName(),
            meeting.getDescription(), meeting.getMaxParticipants(),
            meeting.getCurrentParticipants()))
        .toList();
  }

  @Operation(summary = "모임 수정", description = "모임을 수정합니다. (생성자만 가능)")
  @ApiResponse(responseCode = "200", description = "모임이 성공적으로 수정되었습니다.")
  @ApiResponse(responseCode = "401", description = "생성자가 아니거나 유효하지 않은 토큰")
  @Transactional
  public MeetingResponseDto updateMeeting(String token, Long meetingId,
      UpdateMeetingRequestDto updateMeetingRequestDto) {
    Optional<User> userOpt = getCurrentUser(token);
    if (userOpt.isEmpty()) {
      return null;  // 유효하지 않은 토큰
    }

    User currentUser = userOpt.get();
    Optional<Meeting> meetingOptional = meetingRepository.findById(meetingId);
    if (meetingOptional.isEmpty()) {
      return null;  // 모임이 존재하지 않음
    }

    Meeting meeting = meetingOptional.get();
    if (!meeting.getCreator().equals(currentUser)) {
      return null;  // 생성자가 아니면 수정 불가
    }

    meeting.setName(updateMeetingRequestDto.getName());
    meeting.setDescription(updateMeetingRequestDto.getDescription());
    meeting.setMaxParticipants(updateMeetingRequestDto.getMaxParticipants());

    meetingRepository.save(meeting);

    return new MeetingResponseDto(meeting.getId(), meeting.getName(), meeting.getDescription(),
        meeting.getMaxParticipants(), meeting.getCurrentParticipants());
  }

  @Operation(summary = "모임 삭제", description = "모임을 삭제합니다. (생성자만 가능)")
  @ApiResponse(responseCode = "200", description = "모임이 성공적으로 삭제되었습니다.")
  @Transactional
  public void deleteMeeting(String token, Long meetingId) {
    Optional<User> userOpt = getCurrentUser(token);
    if (userOpt.isEmpty()) {
      return;  // 유효하지 않은 토큰
    }

    User currentUser = userOpt.get();
    Optional<Meeting> meetingOptional = meetingRepository.findById(meetingId);
    if (meetingOptional.isEmpty()) {
      return;  // 모임이 존재하지 않음
    }

    Meeting meeting = meetingOptional.get();
    if (!meeting.getCreator().equals(currentUser)) {
      return;  // 생성자가 아니면 삭제 불가
    }

    meetingRepository.delete(meeting);
  }
}
