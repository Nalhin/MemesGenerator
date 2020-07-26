package com.memes.auth;

import com.memes.user.User;
import com.memes.user.UserService;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    Authentication mockAuth = mock(Authentication.class);
    AuthUser mockAuthUser = mock(AuthUser.class);
    when(jwtService.sign(user.getUsername())).thenReturn(token);
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(mockAuth);
    when(mockAuthUser.getUser()).thenReturn(user);
    when(mockAuth.getPrincipal()).thenReturn(mockAuthUser);

    Pair<User, String> result = authService.login(user.getUsername(), user.getPassword());

    assertEquals(token, result.getSecond());
    verify(authenticationManager)
        .authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
  }

  @Test
  void signUp() {
    when(jwtService.sign(user.getUsername())).thenReturn(token);
    when(userService.save(any(User.class))).thenReturn(user);

    Pair<User, String> result = authService.signUp(user);

    assertEquals(user, result.getFirst());
    assertEquals(token, result.getSecond());
    verify(passwordEncoder).encode(anyString());
    verify(userService).save(any(User.class));
  }
}
