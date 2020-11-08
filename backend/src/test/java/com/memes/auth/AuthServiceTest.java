package com.memes.auth;

import com.memes.jwt.JwtService;
import com.memes.jwt.model.JwtPayload;
import com.memes.user.User;
import com.memes.user.UserRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import static com.memes.jwt.test.JwtTestFactory.jwtPayload;
import static com.memes.security.test.SecurityUserTestFactory.authentication;
import static com.memes.user.test.UserTestFactory.user;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

  @Mock private JwtService jwtService;
  @Mock private AuthenticationManager authenticationManager;
  @Mock private PasswordEncoder passwordEncoder;
  @Mock private UserRepository userRepository;

  @InjectMocks private AuthService authService;

  private User user;
  private JwtPayload jwtPayload;

  @BeforeEach
  void setUp() {
    user = user().build();
    jwtPayload = jwtPayload().build();
  }

  @Nested
  class Login {

    @Test
    @DisplayName(
        "Should authenticate user with valid credentials and return User and auth jwtPayload")
    void validCredentials() {
      when(jwtService.sign(user.getUsername())).thenReturn(jwtPayload);
      when(authenticationManager.authenticate(any())).thenReturn(authentication(user));

      Pair<User, JwtPayload> result = authService.login(user.getUsername(), user.getPassword());

      assertThat(result.getSecond()).isEqualTo(jwtPayload);
      verify(authenticationManager)
          .authenticate(
              new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
    }
  }

  @Nested
  class SignUp {

    @Test
    @DisplayName(
        "Should create user and return User and auth jwtPayload when username and email are unique")
    void uniqueUser() {
      when(jwtService.sign(user.getUsername())).thenReturn(jwtPayload);
      when(userRepository.existsByEmailOrUsername(user.getEmail(), user.getUsername()))
          .thenReturn(false);
      when(userRepository.save(any())).thenReturn(user);

      Pair<User, JwtPayload> result = authService.signUp(user);

      SoftAssertions.assertSoftly(
          softly -> {
            softly.assertThat(result.getFirst()).isEqualTo(user);
            softly.assertThat(result.getSecond()).isEqualTo(jwtPayload);
          });
      verify(passwordEncoder, times(1)).encode(anyString());
      verify(userRepository, times(1)).save(any());
      verify(userRepository, times(1)).existsByEmailOrUsername(user.getEmail(), user.getUsername());
    }

    @Test
    @DisplayName("Should throw ResponseStatusException if username or email is taken")
    void usernameOrEmailTaken() {
      when(userRepository.existsByEmailOrUsername(user.getEmail(), user.getUsername()))
          .thenReturn(true);

      assertThatThrownBy(() -> authService.signUp(user))
          .isInstanceOf(ResponseStatusException.class);

      verify(userRepository, times(1)).existsByEmailOrUsername(user.getEmail(), user.getUsername());
    }
  }
}
