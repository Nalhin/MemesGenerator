package com.memes.security.model;

import com.memes.user.User;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

public class AuthenticatedUser extends org.springframework.security.core.userdetails.User
    implements AppUser {

  private final User user;

  @Builder(builderMethodName = "authBuilder")
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

  public User getUser() {
    return this.user;
  }

  public Long getId() {
    return this.user.getId();
  }

  @Override
  public boolean isAuthenticated() {
    return true;
  }

  @Override
  public void ifAuthenticated(Consumer<AuthenticatedUser> userConsumer) {
    userConsumer.accept(this);
  }

  @Override
  public void ifAnonymous(Consumer<AnonymousUser> userConsumer) {}

  @Override
  public Optional<User> getPresentUser() {
    return Optional.of(user);
  }
}
