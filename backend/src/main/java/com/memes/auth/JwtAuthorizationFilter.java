package com.memes.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

  private static final String AUTH_HEADER = "Authorization";
  private static final String AUTH_PREFIX = "Bearer ";

  private final JwtService jwtService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    String token = getTokenFromHeader(request.getHeader(AUTH_HEADER));

    if (token == null || !jwtService.validate(token)) {
      SecurityContextHolder.clearContext();
      chain.doFilter(request, response);
      return;
    }

    try {
      UsernamePasswordAuthenticationToken auth = jwtService.getAuthentication(token);
      SecurityContextHolder.getContext().setAuthentication(auth);
    } catch (UsernameNotFoundException ignored) {
      SecurityContextHolder.clearContext();
    } finally {
      chain.doFilter(request, response);
    }
  }

  private String getTokenFromHeader(String authHeader) {
    if (StringUtils.hasText(authHeader) && authHeader.startsWith(AUTH_PREFIX)) {
      return authHeader.replace(AUTH_PREFIX, "");
    }
    return null;
  }
}
