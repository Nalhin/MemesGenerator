package com.memes.auth.models;

import com.memes.user.User;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Optional;

public class AuthenticatedUser extends org.springframework.security.core.userdetails.User
    implements AuthUser {

  private final User user;

  @Builder(builderMethodName = "AuthUserBuilder")
  public AuthenticatedUser(
      String username,
      String password,
      boolean enabled,
      boolean accountNonExpired,
      boolean credentialsNonExpired,
      boolean accountNonLocked,
      Collection<? extends GrantedAuthority> authorities,
      User user) {
    super(
        username,
        password,
        enabled,
        accountNonExpired,
        credentialsNonExpired,
        accountNonLocked,
        authorities);
    this.user = user;
  }

  @Override
  public boolean isAuthenticated() {
    return true;
  }

  public User getPresentUser() {
    return this.user;
  }

  @Override
  public Optional<User> getUser() {
    return Optional.of(this.user);
  }

  @Override
  public Optional<AuthenticatedUser> getAuthenticatedUser() {
    return Optional.of(this);
  }
}
