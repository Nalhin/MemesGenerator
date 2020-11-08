package com.memes.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class JwtClockTest {

  @Nested
  @DisplayName("now")
  class Now {

    @Test
    @DisplayName("Should return current date")
    void returnsCurrentDate() {
      String expectedDate = "2018-08-19T16:45:42.00Z";
      JwtClock clock = new JwtClock(Clock.fixed(Instant.parse(expectedDate), ZoneId.of("CET")));

      Date actualResult = clock.now();

      assertThat(actualResult).hasSameTimeAs(expectedDate);
    }
  }
}
