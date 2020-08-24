package com.memes.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
  @Mock private UserDetailsService detailsService;
  @Mock private SecurityContextFacade securityContextFacade;
  @InjectMocks private JwtAuthorizationFilter jwtAuthorizationFilter;

  @Test
  void doFilterInternal_ValidTokenAndUserFound_ContextCalledWithMockAuth()
      throws ServletException, IOException {
    UserDetails mockDetails = mock(UserDetails.class);
    when(request.getHeader(AUTH_HEADER)).thenReturn("Bearer token");
    when(jwtService.validate(any())).thenReturn(true);
    when(jwtService.resolveUsernameFromToken("token")).thenReturn("username");
    when(detailsService.loadUserByUsername("username")).thenReturn(mockDetails);

    jwtAuthorizationFilter.doFilterInternal(
        request, mock(HttpServletResponse.class), mock(FilterChain.class));

    verify(securityContextFacade, times(1))
        .setAuthentication(any(UsernamePasswordAuthenticationToken.class));
    verify(securityContextFacade, never()).clearAuthentication();
  }

  @Test
  void doFilterInternal_MissingToken_ContextNotSet() throws ServletException, IOException {
    when(request.getHeader(AUTH_HEADER)).thenReturn("");

    jwtAuthorizationFilter.doFilterInternal(
        request, mock(HttpServletResponse.class), mock(FilterChain.class));

    verify(jwtService, never()).resolveUsernameFromToken(any());
    verify(securityContextFacade, never()).setAuthentication(any());
    verify(securityContextFacade, times(1)).clearAuthentication();
  }

  @Test
  void doFilterInternal_InvalidToken_ContextNotSet() throws ServletException, IOException {
    when(request.getHeader(AUTH_HEADER)).thenReturn("Bea dsadsa");

    jwtAuthorizationFilter.doFilterInternal(
        request, mock(HttpServletResponse.class), mock(FilterChain.class));

    verify(jwtService, never()).validate(any());
    verify(securityContextFacade, never()).setAuthentication(any());
    verify(securityContextFacade, times(1)).clearAuthentication();
  }

  @Test
  void doFilterInternal_UserNotFound_ContextNotSet() throws ServletException, IOException {
    when(request.getHeader(AUTH_HEADER)).thenReturn("Bearer token");
    when(jwtService.validate(any())).thenReturn(true);
    when(jwtService.resolveUsernameFromToken("token")).thenReturn("username");
    when(detailsService.loadUserByUsername("username")).thenThrow(UsernameNotFoundException.class);

    jwtAuthorizationFilter.doFilterInternal(
        request, mock(HttpServletResponse.class), mock(FilterChain.class));

    verify(securityContextFacade, never()).setAuthentication(any());
    verify(securityContextFacade, times(1)).clearAuthentication();
  }
}
