package com.memes.auth;

import com.memes.auth.dto.AuthResponseDto;
import com.memes.auth.dto.LoginUserDto;
import com.memes.auth.dto.SignUpUserDto;
import com.memes.auth.test.AuthTestFactory;
import com.memes.jwt.model.JwtPayload;
import com.memes.jwt.test.JwtTestFactory;
import com.memes.test.utils.MockSecurityConfig;
import com.memes.user.User;
import com.memes.user.UserMapperImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.memes.test.matchers.ResponseBodyMatchers.responseBody;
import static com.memes.test.utils.RequestUtils.asJSON;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = {AuthController.class})
@Import({AuthMapperImpl.class, UserMapperImpl.class})
class AuthControllerTest extends MockSecurityConfig {

  @Autowired private MockMvc mockMvc;

  @MockBean private AuthService authService;

  @Autowired private AuthMapper authMapper;

  @Nested
  class Login {
    @Test
    @DisplayName("Should return OK (200) status code and AuthResponseDto")
    void returns200() throws Exception {
      LoginUserDto providedRequestBody = AuthTestFactory.loginUserDto().build();
      Pair<User, JwtPayload> serviceReturn = AuthTestFactory.authPair();
      when(authService.login(providedRequestBody.getUsername(), providedRequestBody.getPassword()))
          .thenReturn(serviceReturn);

      mockMvc
          .perform(
              post("/auth/login")
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                  .content(asJSON(providedRequestBody)))
          .andExpect(status().isOk())
          .andExpect(
              responseBody()
                  .containsObjectAsJson(
                      authMapper.authPairToResponse(serviceReturn), AuthResponseDto.class));
    }

    @Test
    @DisplayName(
        "Should return BAD_REQUEST (400) status code and validation errors when request body is invalid")
    void returns403() throws Exception {
      LoginUserDto providedRequestBody =
          AuthTestFactory.loginUserDto().password(null).username(null).build();

      mockMvc
          .perform(
              post("/auth/login")
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                  .content(asJSON(providedRequestBody)))
          .andExpect(status().isBadRequest())
          .andExpect(responseBody().containsValidationErrors("password", "username"));
    }
  }

  @Nested
  class SignUp {

    @Test
    @DisplayName("Should return OK (200) status code and AuthResponseDto")
    void returns200() throws Exception {
      SignUpUserDto providedRequestBody = AuthTestFactory.signUpUserDto().build();
      Pair<User, JwtPayload> serviceResult = AuthTestFactory.authPair();
      when(authService.signUp(authMapper.signUpUserDtoToUser(providedRequestBody)))
          .thenReturn(serviceResult);

      mockMvc
          .perform(
              post("/auth/sign-up")
                  .contentType(MediaType.APPLICATION_JSON_VALUE)
                  .content(asJSON(providedRequestBody)))
          .andExpect(status().isOk())
          .andExpect(
              responseBody()
                  .containsObjectAsJson(
                      authMapper.authPairToResponse(serviceResult), AuthResponseDto.class));

      verify(authService, times(1)).signUp(any(User.class));
    }

    @Test
    @DisplayName(
        "Should return BAD_REQUEST (400) status code and validation errors when request body is invalid")
    void returns400() throws Exception {
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
  }
}
