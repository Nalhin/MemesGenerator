package com.memes.jwt;

import com.memes.jwt.model.JwtPayload;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {

  private final String jwt_key;
  private final Long validityInMs;
  private final Clock clock;

  public JwtService(
      @Value("${jwt.secret:jwt}") String jwt_key,
      @Value("${jwt.validity:3600000}") Long validity,
      Clock clock) {
    this.jwt_key = encodeKey(jwt_key);
    this.validityInMs = validity;
    this.clock = clock;
  }

  private String encodeKey(String key) {
    return Base64.getEncoder().encodeToString(key.getBytes());
  }

  public JwtPayload sign(String username) {
    Date now = clock.now();
    Date expiration = new Date(now.getTime() + validityInMs);

    String jwt =
        Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(SignatureAlgorithm.HS512, jwt_key)
            .compact();

    return JwtPayload.builder()
        .accessToken(jwt)
        .exp(expiration.getTime())
        .iat(now.getTime())
        .sub(username)
        .build();
  }

  public Optional<String> resolveUsernameFromToken(String token) {
    try {
      return Optional.of(
          Jwts.parser()
              .setSigningKey(jwt_key)
              .setClock(clock)
              .parseClaimsJws(token)
              .getBody()
              .getSubject());
    } catch (JwtException ex) {
      return Optional.empty();
    }
  }

  public boolean validate(String token) {
    try {
      Jwts.parser().setSigningKey(jwt_key).setClock(clock).parseClaimsJws(token);
      return true;
    } catch (JwtException ex) {
      return false;
    }
  }
}
