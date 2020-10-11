package com.memes.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

  @Nested
  class DoFilterInternal {
    @Test
    @DisplayName("Should set authentication token if token is valid ans user is found")
    void validTokenAndUserFound() throws ServletException, IOException {
      when(request.getHeader(AUTH_HEADER)).thenReturn("Bearer token");
      when(jwtService.validate(any())).thenReturn(true);
      when(jwtService.resolveUsernameFromToken("token")).thenReturn("username");
      when(detailsService.loadUserByUsername("username")).thenReturn(mock(UserDetails.class));

      jwtAuthorizationFilter.doFilterInternal(
          request, mock(HttpServletResponse.class), mock(FilterChain.class));

      verify(securityContextFacade, times(1))
          .setAuthentication(any(UsernamePasswordAuthenticationToken.class));
      verify(securityContextFacade, never()).clearAuthentication();
    }

    @Test
    @DisplayName("Should not set authentication context if token is missing")
    void missingToken() throws ServletException, IOException {
      when(request.getHeader(AUTH_HEADER)).thenReturn("");

      jwtAuthorizationFilter.doFilterInternal(
          request, mock(HttpServletResponse.class), mock(FilterChain.class));

      verify(jwtService, never()).resolveUsernameFromToken(any());
      verify(securityContextFacade, never()).setAuthentication(any());
      verify(securityContextFacade, times(1)).clearAuthentication();
    }

    @Test
    @DisplayName("Should not set authentication context if token is invallid")
    void invalidToken() throws ServletException, IOException {
      when(request.getHeader(AUTH_HEADER)).thenReturn("Bea dsadsa");

      jwtAuthorizationFilter.doFilterInternal(
          request, mock(HttpServletResponse.class), mock(FilterChain.class));

      verify(jwtService, never()).validate(any());
      verify(securityContextFacade, never()).setAuthentication(any());
      verify(securityContextFacade, times(1)).clearAuthentication();
    }

    @Test
    @DisplayName("Should not set authentication context if user is not found")
    void userNotFound() throws ServletException, IOException {
      when(request.getHeader(AUTH_HEADER)).thenReturn("Bearer token");
      when(jwtService.validate(any())).thenReturn(true);
      when(jwtService.resolveUsernameFromToken("token")).thenReturn("username");
      when(detailsService.loadUserByUsername("username"))
          .thenThrow(UsernameNotFoundException.class);

      jwtAuthorizationFilter.doFilterInternal(
          request, mock(HttpServletResponse.class), mock(FilterChain.class));

      verify(securityContextFacade, never()).setAuthentication(any());
      verify(securityContextFacade, times(1)).clearAuthentication();
    }
  }
}
