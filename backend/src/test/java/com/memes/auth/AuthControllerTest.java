package com.memes.auth;

import com.memes.auth.dto.AuthResponseDto;
import com.memes.auth.dto.LoginUserDto;
import com.memes.auth.dto.SignUpUserDto;
import com.memes.shared.utils.CustomModelMapper;
import com.memes.user.User;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

  @Mock private AuthService authService;

  private AuthController authController;
  private User user;

  @BeforeEach
  void setUp() {
    authController = new AuthController(authService, new CustomModelMapper());
    user = new EasyRandom().nextObject(User.class);
  }

  @Test
  void login_OperationSuccessful_ReturnsUserAndToken() {
    String token = "token";
    LoginUserDto loginUserDto = new EasyRandom().nextObject(LoginUserDto.class);
    when(authService.login(anyString(), anyString())).thenReturn(Pair.of(user, token));

    ResponseEntity<AuthResponseDto> result = authController.login(loginUserDto);

    assertNotNull(result.getBody());
    assertEquals(token, result.getBody().getToken());
  }

  @Test
  void signUp_OperationSuccessful_ReturnsUserDto() {
    String token = "token";
    SignUpUserDto signUpUserDto = new EasyRandom().nextObject(SignUpUserDto.class);
    when(authService.signUp(any())).thenReturn(Pair.of(user, token));

    ResponseEntity<AuthResponseDto> result = authController.signUp(signUpUserDto);

    assertNotNull(result.getBody());
    assertEquals(user.getUsername(), result.getBody().getUser().getUsername());
    assertEquals(token, result.getBody().getToken());
  }
}
