package com.memes.security.test;

import com.memes.security.model.AppUser;
import com.memes.security.model.AuthenticatedUser;
import com.memes.user.User;
import com.memes.user.test.UserTestFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.Collections;

public class SecurityUserTestFactory {

  public static AuthenticatedUser.AuthenticatedUserBuilder authenticatedUser(User user) {
    return AuthenticatedUser.authBuilder()
        .username(user.getUsername())
        .user(user)
        .password(user.getPassword())
        .accountNonExpired(true)
        .accountNonLocked(true)
        .credentialsNonExpired(true)
        .enabled(true)
        .authorities(Collections.emptyList());
  }

  public static AuthenticatedUser.AuthenticatedUserBuilder authenticatedUser() {
    return authenticatedUser(UserTestFactory.user().build());
  }

  public static Authentication authentication(User user) {
    return new UsernamePasswordAuthenticationToken(
        authenticatedUser(user).build(), null, new ArrayList<>());
  }

  public static Authentication authentication(AppUser user) {
    return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
  }
}
