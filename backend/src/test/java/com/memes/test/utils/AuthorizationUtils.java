package com.memes.test.utils;

import com.memes.jwt.JwtClock;
import com.memes.jwt.JwtService;
import io.restassured.http.Header;
import org.springframework.http.HttpHeaders;

import java.time.Clock;
import java.time.Instant;

public class AuthorizationUtils {

  private static final JwtService jwtService =
      new JwtService("jwt", 3600000L, new JwtClock(Clock.systemUTC()));

  public static Header authHeaders(String username) {
    return new Header("Authorization", "Bearer " + jwtService.sign(username).getAccessToken());
  }

  public static boolean validateToken(String token) {
    return jwtService.validate(token);
  }
}
