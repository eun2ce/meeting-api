package com.example.swaggerexam;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.swaggerexam.controller.AuthController;
import com.example.swaggerexam.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
class SwaggerexamApplicationTests {

  private MockMvc mockMvc;

  @Mock
  private UserService userService;

  @InjectMocks
  private AuthController authController;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
  }

  @Test
  void testLogin_Success() throws Exception {
    // given: 테스트용 요청 데이터
    String email = "test@example.com";
    String password = "password123";
    String mockJwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    // when: mock 서비스 동작 설정
    when(userService.login(email, password)).thenReturn(mockJwtToken);

    // then: 로그인 요청을 보내고 응답 결과 검증
    mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}"))
        .andExpect(status().isOk())  // HTTP 상태 200 OK
        .andExpect(
            jsonPath("$.token").value(mockJwtToken));
  }
}
