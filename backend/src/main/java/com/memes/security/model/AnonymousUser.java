package com.memes.security.model;

import com.memes.user.User;

import java.util.Optional;
import java.util.function.Consumer;

public class AnonymousUser implements AppUser {
  @Override
  public boolean isAuthenticated() {
    return false;
  }

  @Override
  public void ifAuthenticated(Consumer<AuthenticatedUser> userConsumer) {}

  @Override
  public void ifAnonymous(Consumer<AnonymousUser> userConsumer) {
    userConsumer.accept(this);
  }

  @Override
  public Optional<User> getPresentUser() {
    return Optional.empty();
  }
}
