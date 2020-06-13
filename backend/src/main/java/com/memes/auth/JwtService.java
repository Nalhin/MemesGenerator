package com.memes.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

  private String JWT_KEY = "jwt";
  private final Long VALIDITY_IN_MS = 3600000L;

  private final AuthUserDetailsService authUserDetailsService;

  public JwtService(AuthUserDetailsService authUserDetailsService) {
    this.authUserDetailsService = authUserDetailsService;
  }

  @PostConstruct
  protected void init() {
    JWT_KEY = Base64.getEncoder().encodeToString(JWT_KEY.getBytes());
  }

  public String sign(String username) {
    Claims claims = Jwts.claims().setSubject(username);
    Date now = new Date();
    Date validity = new Date(now.getTime() + VALIDITY_IN_MS);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(validity)
        .signWith(SignatureAlgorithm.HS512, JWT_KEY)
        .compact();
  }

  public UsernamePasswordAuthenticationToken getAuthentication(String token) {
    UserDetails userDetails = authUserDetailsService.loadUserByUsername(getUsername(token));
    System.out.println(userDetails.toString());

    return new UsernamePasswordAuthenticationToken(userDetails, null, new ArrayList<>());
  }

  private String getUsername(String token) {
    return Jwts.parser().setSigningKey(JWT_KEY).parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validate(String token) {
    try {
      Jwts.parser().setSigningKey(JWT_KEY).parseClaimsJws(token);
      return true;
    } catch (JwtException ex) {
      return false;
    }
  }
}
