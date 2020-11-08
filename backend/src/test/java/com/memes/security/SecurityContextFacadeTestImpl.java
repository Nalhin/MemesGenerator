package com.memes.security;

import com.memes.security.model.AnonymousUser;
import com.memes.security.model.AppUser;
import org.springframework.security.core.Authentication;

public class SecurityContextFacadeTestImpl implements SecurityContextFacade {

  private Authentication authentication;

  @Override
  public void setAuthentication(Authentication authentication) {
    this.authentication = authentication;
  }

  @Override
  public void clearAuthentication() {
    authentication = null;
  }

  @Override
  public Authentication getAuthentication() {
    return authentication;
  }

  @Override
  public AppUser getAppUser() {
    if (authentication == null) {
      return new AnonymousUser();
    }
    return (AppUser) authentication.getPrincipal();
  }
}
