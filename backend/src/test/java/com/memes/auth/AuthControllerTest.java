package com.memes.auth;

import com.memes.auth.dto.AuthResponseDto;
import com.memes.auth.dto.LoginUserDto;
import com.memes.auth.dto.RegisterUserDto;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

  @Mock private AuthService authService;
  private AuthController authController;

  @BeforeEach
  void setUp() {
    authController = new AuthController(authService, new ModelMapper());
  }

  @Test
  void login() {
    String token = "token";
    LoginUserDto loginUserDto = new EasyRandom().nextObject(LoginUserDto.class);
    when(authService.login(anyString(), anyString())).thenReturn(token);

    AuthResponseDto result = authController.login(loginUserDto);

    assertEquals(token, result.getToken());
  }

  @Test
  void register() {
    String token = "token";
    RegisterUserDto registerUserDto = new EasyRandom().nextObject(RegisterUserDto.class);
    when(authService.register(any())).thenReturn(token);

    AuthResponseDto result = authController.register(registerUserDto);

    assertEquals(token, result.getToken());
  }
}
