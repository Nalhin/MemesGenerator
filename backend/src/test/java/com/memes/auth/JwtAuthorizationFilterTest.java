package com.memes.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthorizationFilterTest {

  private final String AUTH_HEADER = "Authorization";

  @Mock private JwtService jwtService;
  @Mock private HttpServletRequest request;
  @InjectMocks private JwtAuthorizationFilter jwtAuthorizationFilter;

  @Mock private SecurityContext securityContext;

  @BeforeEach
  void setUp() {
    SecurityContextHolder.setContext(securityContext);
  }

  @Test
  void doFilterInternal_ValidTokenAndUserFound_ContextCalledWithMockAuth()
      throws ServletException, IOException {
    UsernamePasswordAuthenticationToken mockAuth = mock(UsernamePasswordAuthenticationToken.class);
    when(request.getHeader(AUTH_HEADER)).thenReturn("Bearer token");
    when(jwtService.validate("token")).thenReturn(true);
    when(jwtService.getAuthentication("token")).thenReturn(mockAuth);

    jwtAuthorizationFilter.doFilterInternal(
        request, mock(HttpServletResponse.class), mock(FilterChain.class));

    verify(securityContext).setAuthentication(mockAuth);
  }

  @Test
  void doFilterInternal_MissingToken_ContextNotSet() throws ServletException, IOException {
    when(request.getHeader(AUTH_HEADER)).thenReturn("");

    jwtAuthorizationFilter.doFilterInternal(
        request, mock(HttpServletResponse.class), mock(FilterChain.class));

    verify(jwtService, never()).getAuthentication(any());
    verify(securityContext, never()).setAuthentication(any());
  }

  @Test
  void doFilterInternal_InvalidToken_ContextNotSet() throws ServletException, IOException {
    when(request.getHeader(AUTH_HEADER)).thenReturn("Bea dsadsa");

    jwtAuthorizationFilter.doFilterInternal(
        request, mock(HttpServletResponse.class), mock(FilterChain.class));

    verify(jwtService, never()).getAuthentication(any());
    verify(securityContext, never()).setAuthentication(any());
  }

  @Test
  void doFilterInternal_UserNotFound_ContextNotSet() throws ServletException, IOException {
    when(request.getHeader(AUTH_HEADER)).thenReturn("Bearer token");
    when(jwtService.validate(any())).thenReturn(true);
    when(jwtService.getAuthentication("token")).thenThrow(UsernameNotFoundException.class);

    jwtAuthorizationFilter.doFilterInternal(
        request, mock(HttpServletResponse.class), mock(FilterChain.class));

    verify(securityContext, never()).setAuthentication(any());
  }
}
