package com.memes.test.utils;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class TestClockUtils {

  public static final String TEST_CLOCK_TIME = "2018-08-19T16:45:42.00Z";

  public static Clock testClock() {
    return Clock.fixed(Instant.parse(TEST_CLOCK_TIME), ZoneId.of("CET"));
  }
}
