package com.memes.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memes.auth.dto.AuthResponseDto;
import com.memes.auth.dto.LoginUserDto;
import com.memes.auth.dto.SignUpUserDto;
import com.memes.user.User;
import com.memes.user.UserRepository;
import com.memes.user.UserTestBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static com.memes.testutils.matchers.ResponseBodyMatchers.responseBody;
import static com.memes.testutils.utils.RequestUtils.asJSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private UserRepository userRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  @Test
  void login_ValidCredentials_Returns200AndUserWithToken() throws Exception {
    LoginUserDto loginUserDto = AuthTestBuilder.loginUserDto().build();
    userRepository.save(
        UserTestBuilder.user()
            .username(loginUserDto.getUsername())
            .password(passwordEncoder.encode(loginUserDto.getPassword()))
            .build());

    mockMvc
        .perform(
            post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJSON(loginUserDto)))
        .andExpect(status().isOk())
        .andExpect(responseBody().containsValidJWT());
  }
  @Test
  void login_InvalidCredentials_Returns403() throws Exception {
    LoginUserDto loginUserDto = AuthTestBuilder.loginUserDto().build();
    userRepository.save(
        UserTestBuilder.user()
            .username(loginUserDto.getUsername())
            .build());

    mockMvc
        .perform(
            post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJSON(loginUserDto)))
        .andExpect(status().isForbidden());
  }

  @Test
  void signUp_ValidInput_Returns200andUserWithToken() throws Exception {
    SignUpUserDto signUpUserDto = AuthTestBuilder.signUpUserDto().build();

    mockMvc
        .perform(
            post("/auth/sign-up")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJSON(signUpUserDto)))
        .andExpect(status().isOk())
        .andExpect(responseBody().containsValidJWT());

    assertThat(userRepository.findOneByUsername(signUpUserDto.getUsername())).isPresent();
  }

  @Test
  void signUp_UsernameTaken_Returns200andUserWithToken() throws Exception {
    SignUpUserDto signUpUserDto = AuthTestBuilder.signUpUserDto().build();

    mockMvc
            .perform(
                    post("/auth/sign-up")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(asJSON(signUpUserDto)))
            .andExpect(status().isOk())
            .andExpect(responseBody().containsValidJWT());

    assertThat(userRepository.findOneByUsername(signUpUserDto.getUsername())).isPresent();
  }

  @Test
  void signUp_EmailTaken_Returns409() throws Exception {
    SignUpUserDto signUpUserDto = AuthTestBuilder.signUpUserDto().build();
    userRepository.save(
            UserTestBuilder.user()
                    .email(signUpUserDto.getEmail())
                    .build());

    mockMvc
            .perform(
                    post("/auth/sign-up")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(asJSON(signUpUserDto)))
            .andExpect(status().isConflict());
  }
}
