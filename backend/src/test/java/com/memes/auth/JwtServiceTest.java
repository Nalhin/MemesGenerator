package com.memes.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

  @Mock private AuthUserDetailsService authUserDetailsService;

  private JwtService jwtService;

  @BeforeEach
  void setUp() {
    jwtService = new JwtService(authUserDetailsService);
    jwtService.init();
  }

  @Test
  void sign_ValidUsername_ReturnsValidToken() {
    String username = "username";

    String result = jwtService.sign(username);

    assertTrue(jwtService.validate(result));
  }

  @Test
  void getAuthentication_ValidToken_ReturnAuthTokenWithUser() {
    UserDetails userDetails = mock(User.class);
    String username = "username";
    String token = jwtService.sign(username);
    when(authUserDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

    UsernamePasswordAuthenticationToken result = jwtService.getAuthentication(token);

    assertEquals(userDetails, result.getPrincipal());
  }

  @Test
  void validate_ValidToken_ReturnsTrue() {
    String token = jwtService.sign("username");

    boolean result = jwtService.validate(token);

    assertTrue(result);
  }

  @Test
  void validate_InvalidToken_ReturnsFalse() {
    String token = "invalid";

    boolean result = jwtService.validate(token);

    assertFalse(result);
  }
}
