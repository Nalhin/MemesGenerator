package com.memes.auth;

import com.memes.auth.dto.AuthResponseDto;
import com.memes.auth.dto.LoginUserDto;
import com.memes.auth.dto.SignUpUserDto;
import com.memes.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static com.memes.testutils.matchers.ResponseBodyMatchers.responseBody;
import static com.memes.testutils.utils.RequestUtils.asJSON;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private AuthService authService;

  @Autowired private AuthMapper authMapper;

  @Test
  void login_ValidInput_Returns200AndUserWithToken() throws Exception {
    LoginUserDto loginUserDto = AuthTestBuilder.loginUserDto().build();
    Pair<User, String> serviceReturn = AuthTestBuilder.authPair();
    AuthResponseDto expected = authMapper.authPairToUserResponseDto(serviceReturn);
    when(authService.login(loginUserDto.getUsername(), loginUserDto.getPassword()))
        .thenReturn(serviceReturn);

    mockMvc
        .perform(
            post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJSON(loginUserDto)))
        .andExpect(status().isOk())
        .andExpect(responseBody().containsObjectAsJson(expected, AuthResponseDto.class));
  }

  @Test
  void login_NullFields_ReturnsValidationErrors() throws Exception {
    LoginUserDto loginUserDto =
        AuthTestBuilder.loginUserDto().password(null).username(null).build();

    mockMvc
        .perform(
            post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJSON(loginUserDto)))
        .andExpect(status().isBadRequest())
        .andExpect(responseBody().containsValidationErrors("password", "username"));
  }

  @Test
  void signUp_InvalidInput_Returns400AndValidationErrors() throws Exception {
    SignUpUserDto signUpUserDto =
        SignUpUserDto.builder().email("invalid").password("short").username(null).build();

    mockMvc
        .perform(
            post("/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJSON(signUpUserDto)))
        .andExpect(status().isBadRequest())
        .andExpect(responseBody().containsValidationErrors("email", "password", "username"));
  }

  @Test
  void signUp_ValidInput_Returns200andUserWithToken() throws Exception {
    SignUpUserDto signUpUserDto = AuthTestBuilder.signUpUserDto().build();
    Pair<User, String> serviceResult = AuthTestBuilder.authPair();
    AuthResponseDto expected = authMapper.authPairToUserResponseDto(serviceResult);
    when(authService.signUp(authMapper.signUpUserDtoToUser(signUpUserDto)))
        .thenReturn(serviceResult);

    mockMvc
        .perform(
            post("/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJSON(signUpUserDto)))
        .andExpect(status().isOk())
        .andExpect(responseBody().containsObjectAsJson(expected, AuthResponseDto.class));

    verify(authService, times(1)).signUp(any(User.class));
  }
}
