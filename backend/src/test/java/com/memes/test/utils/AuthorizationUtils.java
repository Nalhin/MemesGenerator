package com.memes.test.utils;

import com.memes.auth.JwtService;
import org.springframework.http.HttpHeaders;

public class AuthorizationUtils {

  private static final JwtService jwtService = new JwtService();

  static {
    jwtService.init();
  }

  public static HttpHeaders authHeaders(String username) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("Authorization", "Bearer " + jwtService.sign(username));
    return httpHeaders;
  }

  public static boolean validateToken(String token) {
    return jwtService.validate(token);
  }
}
