package com.memes.security;

import com.memes.security.model.AppUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextFacadeImpl implements SecurityContextFacade {

  public void setAuthentication(Authentication authentication) {
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  public void clearAuthentication() {
    SecurityContextHolder.clearContext();
  }

  public Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  public AppUser getAppUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    return (AppUser) authentication.getPrincipal();
  }
}
