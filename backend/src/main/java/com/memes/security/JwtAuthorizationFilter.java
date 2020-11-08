package com.memes.security;

import com.memes.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

  private static final String AUTH_HEADER = "Authorization";

  private final BearerHeaderTokenResolver bearerTokenHeaderResolver;
  private final JwtService jwtService;
  private final UserDetailsService authUserDetailsService;
  private final SecurityContextFacade securityContextFacade;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {

    bearerTokenHeaderResolver
        .resolveTokenFromHeader(request.getHeader(AUTH_HEADER))
        .flatMap(jwtService::resolveUsernameFromToken)
        .flatMap(this::getAuthentication)
        .ifPresentOrElse(
            securityContextFacade::setAuthentication, securityContextFacade::clearAuthentication);

    chain.doFilter(request, response);
  }

  private Optional<UsernamePasswordAuthenticationToken> getAuthentication(String username) {
    try {
      UserDetails UserDetails = this.authUserDetailsService.loadUserByUsername(username);
      return Optional.of(
          new UsernamePasswordAuthenticationToken(UserDetails, null, new ArrayList<>()));
    } catch (UsernameNotFoundException e) {
      return Optional.empty();
    }
  }
}
