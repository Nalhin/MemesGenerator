package com.memes.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

  private JwtService jwtService;

  @BeforeEach
  void setUp() {
    jwtService = new JwtService();
    jwtService.init();
  }

  @Test
  void sign_ValidUsername_ReturnsValidToken() {
    String username = "username";

    String result = jwtService.sign(username);

    assertThat(jwtService.validate(result)).isTrue();
  }

  @Test
  void getAuthentication_ValidToken_ReturnAuthTokenWithUser() {
    String username = "username";
    String token = jwtService.sign(username);

    String result = jwtService.resolveUsernameFromToken(token);

    assertThat(result).isEqualTo(username);
  }

  @Test
  void validate_ValidToken_ReturnsTrue() {
    String token = jwtService.sign("username");

    boolean result = jwtService.validate(token);

    assertThat(result).isTrue();
  }

  @Test
  void validate_InvalidToken_ReturnsFalse() {
    String token = "invalid";

    boolean result = jwtService.validate(token);

    assertThat(result).isFalse();
  }
}
