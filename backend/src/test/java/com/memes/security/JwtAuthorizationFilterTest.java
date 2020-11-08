package com.memes.security;

import com.memes.jwt.JwtClock;
import com.memes.jwt.JwtService;
import com.memes.security.model.AuthenticatedUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.memes.security.test.SecurityUserTestFactory.authenticatedUser;
import static com.memes.test.utils.TestClockUtils.testClock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthorizationFilterTest {

  private final String AUTH_HEADER = "Authorization";

  @Mock private HttpServletRequest request;
  @Mock private UserDetailsService detailsService;
  private SecurityContextFacade securityContextFacade;
  private JwtAuthorizationFilter jwtAuthorizationFilter;
  private AuthenticatedUser authenticatedUser;
  private String validToken;

  @BeforeEach
  void setUp() {
    JwtService jwtService = new JwtService("key", 1000L, new JwtClock(testClock()));
    authenticatedUser = authenticatedUser().build();
    validToken = jwtService.sign(authenticatedUser.getUsername()).getAccessToken();
    securityContextFacade = new SecurityContextFacadeTestImpl();
    jwtAuthorizationFilter =
        new JwtAuthorizationFilter(
            new BearerHeaderTokenResolver(), jwtService, detailsService, securityContextFacade);
  }

  @Nested
  @DisplayName("doFilterInternal()")
  class DoFilterInternal {
    @Test
    @DisplayName("Should set authentication when token is valid and user is found")
    void validTokenAndUserFound() throws ServletException, IOException {
      when(request.getHeader(AUTH_HEADER)).thenReturn("Bearer " + validToken);
      when(detailsService.loadUserByUsername(authenticatedUser.getUsername()))
          .thenReturn(authenticatedUser);

      jwtAuthorizationFilter.doFilterInternal(
          request, mock(HttpServletResponse.class), mock(FilterChain.class));

      assertThat(securityContextFacade.getAppUser()).isEqualTo(authenticatedUser);
    }

    @Test
    @DisplayName("Should not set authentication when header is invalid")
    void invalidHeader() throws ServletException, IOException {
      when(request.getHeader(AUTH_HEADER)).thenReturn("Bea dsadsa");

      jwtAuthorizationFilter.doFilterInternal(
          request, mock(HttpServletResponse.class), mock(FilterChain.class));

      verify(detailsService, never()).loadUserByUsername(any());
      assertThat(securityContextFacade.getAuthentication()).isNull();
    }

    @Test
    @DisplayName("Should not set authentication context when token is invalid")
    void invalidToken() throws ServletException, IOException {
      when(request.getHeader(AUTH_HEADER)).thenReturn("Bearer token");

      jwtAuthorizationFilter.doFilterInternal(
          request, mock(HttpServletResponse.class), mock(FilterChain.class));

      verify(detailsService, never()).loadUserByUsername(any());
      assertThat(securityContextFacade.getAuthentication()).isNull();
    }

    @Test
    @DisplayName("Should not set authentication context when user is not found")
    void userNotFound() throws ServletException, IOException {
      when(request.getHeader(AUTH_HEADER)).thenReturn("Bearer " + validToken);
      when(detailsService.loadUserByUsername(authenticatedUser.getUsername()))
          .thenThrow(UsernameNotFoundException.class);

      jwtAuthorizationFilter.doFilterInternal(
          request, mock(HttpServletResponse.class), mock(FilterChain.class));

      verify(detailsService, times(1)).loadUserByUsername(any());
      assertThat(securityContextFacade.getAuthentication()).isNull();
    }
  }
}
