package com.memes.security;

import com.memes.security.model.AppUser;
import org.springframework.security.core.Authentication;

public interface SecurityContextFacade {
  void setAuthentication(Authentication authentication);

  void clearAuthentication();

  Authentication getAuthentication();

  AppUser getAppUser();
}
