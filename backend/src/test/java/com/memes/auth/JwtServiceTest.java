package com.memes.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

  @Nested
  class Sign {

    @Test
    @DisplayName("Should generate and return a valid token")
    void generatesAndReturnsValidToken() {
      String username = "username";

      String actualResult = jwtService.sign(username);

      assertThat(jwtService.validate(actualResult)).isTrue();
    }
  }

  @Nested
  class Validate {

    @Test
    @DisplayName("Should return true when provided token is invalid")
    void validTokenProvided() {
      String token = jwtService.sign("username");

      boolean actualResult = jwtService.validate(token);

      assertThat(actualResult).isTrue();
    }

    @Test
    @DisplayName("Should return false when provided token is invalid")
    void invalidTokenProvided() {
      String token = "invalid";

      boolean actualResult = jwtService.validate(token);

      assertThat(actualResult).isFalse();
    }
  }
}
