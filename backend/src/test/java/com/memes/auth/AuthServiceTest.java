package com.memes.auth;

import com.memes.user.User;
import com.memes.user.UserService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

  @Mock private JwtService jwtService;
  @Mock private AuthenticationManager authenticationManager;
  @Mock private PasswordEncoder passwordEncoder;
  @Mock private UserService userService;

  private AuthService authService;

  private User user;
  private final String token = "token";

  @BeforeEach
  void setUp() {
    authService = new AuthService(jwtService, authenticationManager, passwordEncoder, userService);
    user = new EasyRandom().nextObject(User.class);
  }

  @Test
  void login() {
    when(jwtService.sign(user.getUsername())).thenReturn(token);

    String result = authService.login(user.getUsername(), user.getPassword());

    assertEquals(token, result);
    verify(authenticationManager)
        .authenticate(
            new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword()));
  }

  @Test
  void register() {
    when(jwtService.sign(user.getUsername())).thenReturn(token);

    String result = authService.register(user);

    assertEquals(token, result);
    verify(passwordEncoder).encode(anyString());
    verify(userService).save(any(User.class));
  }
}
