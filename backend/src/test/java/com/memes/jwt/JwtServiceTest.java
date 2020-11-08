package com.memes.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.util.Date;
import java.util.Optional;

import static com.memes.test.utils.TestClockUtils.testClock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class JwtServiceTest {
  private final Clock clock = testClock();
  private final JwtClock jwtClock = new JwtClock(clock);

  private JwtClock getExpiringClock() {
    JwtClock currJwt = mock(JwtClock.class);
    when(currJwt.now())
        .thenReturn(new Date(clock.millis()))
        .thenReturn(new Date(clock.millis() + 6000L));
    return currJwt;
  }

  @Nested
  @DisplayName("sign()")
  class Sign {

    @Test
    @DisplayName("Should return a valid token")
    void returnsToken() {
      JwtService service = new JwtService("jwt", 1000L, jwtClock);

      String actualToken = service.sign("username").getAccessToken();

      assertThat(service.validate(actualToken)).isTrue();
    }

    @Test
    @DisplayName("Should encode username into token")
    void encodesUsernameIntoToken() {
      JwtService service = new JwtService("jwt", 1000L, jwtClock);

      String actualToken = service.sign("username").getAccessToken();

      assertThat(service.resolveUsernameFromToken(actualToken)).contains("username");
    }

    @Test
    @DisplayName("Should return a token that is invalid after expiration date has passed")
    void returnsValidExpiration() {
      JwtService service = new JwtService("jwt", 1000L, getExpiringClock());

      String actualToken = service.sign("username").getAccessToken();

      assertThat(service.validate(actualToken)).isFalse();
    }

    @Test
    @DisplayName("Should calculate expiration date correctly")
    void calculatesExpiration() {
      JwtService service = new JwtService("jwt", 1000L, jwtClock);

      Long actualExpiration = service.sign("username").getExp();

      assertThat(actualExpiration).isEqualTo(clock.millis() + 1000L);
    }
  }

  @Nested
  @DisplayName("resolveUsernameFromToken()")
  class ResolveUsernameFromToken {

    @Test
    @DisplayName("Should return an empty optional when token has expired")
    void tokenExpired() {
      JwtService service = new JwtService("jwt", 1000L, getExpiringClock());
      String token = service.sign("username").getAccessToken();

      Optional<String> actualUsername = service.resolveUsernameFromToken(token);

      assertThat(actualUsername).isEmpty();
    }

    @Test
    @DisplayName("Should return an empty optional when token is invalid")
    void invalidToken() {
      JwtService service = new JwtService("jwt", 1000L, jwtClock);

      Optional<String> actualUsername = service.resolveUsernameFromToken("invalid");

      assertThat(actualUsername).isEmpty();
    }

    @Test
    @DisplayName("Should return an empty optional when token was signed with different key")
    void differentKey() {
      JwtService diffKeyService = new JwtService("diff", 1000L, jwtClock);
      String diffKeyToken = diffKeyService.sign("username").getAccessToken();
      JwtService service = new JwtService("jwt", 1000L, jwtClock);

      Optional<String> actualUsername = service.resolveUsernameFromToken(diffKeyToken);

      assertThat(actualUsername).isEmpty();
    }

    @Test
    @DisplayName("Should resolve and return username encoded into token")
    void resolvedUsername() {
      JwtService service = new JwtService("jwt", 1000L, jwtClock);
      String token = service.sign("username").getAccessToken();

      Optional<String> actualUsername = service.resolveUsernameFromToken(token);

      assertThat(actualUsername).contains("username");
    }
  }

  @Nested
  @DisplayName("validate()")
  class Validate {

    @Test
    @DisplayName("Should return false when token has expired")
    void tokenExpired() {
      JwtService service = new JwtService("jwt", 1000L, getExpiringClock());
      String token = service.sign("username").getAccessToken();

      boolean actualResult = service.validate(token);

      assertThat(actualResult).isFalse();
    }

    @Test
    @DisplayName("Should return false when token is invalid")
    void invalidToken() {
      JwtService service = new JwtService("jwt", 1000L, jwtClock);

      boolean actualResult = service.validate("invalid");

      assertThat(actualResult).isFalse();
    }

    @Test
    @DisplayName("Should return true when token is valid")
    void resolvedUsername() {
      JwtService service = new JwtService("jwt", 1000L, jwtClock);
      String token = service.sign("username").getAccessToken();

      boolean actualResult = service.validate(token);

      assertThat(actualResult).isTrue();
    }

    @Test
    @DisplayName("Should return false when token was signed with different key")
    void differentKey() {
      JwtService diffKeyService = new JwtService("diff", 1000L, jwtClock);
      String diffKeyToken = diffKeyService.sign("username").getAccessToken();
      JwtService service = new JwtService("jwt", 1000L, jwtClock);

      boolean actualResult = service.validate(diffKeyToken);

      assertThat(actualResult).isFalse();
    }
  }
}
