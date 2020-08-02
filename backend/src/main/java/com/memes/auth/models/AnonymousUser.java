package com.memes.auth.models;

import com.memes.user.User;

import java.util.Optional;

public class AnonymousUser implements AuthUser {
  @Override
  public boolean isAuthenticated() {
    return false;
  }

  @Override
  public Optional<User> getUser() {
    return Optional.empty();
  }

  @Override
  public Optional<AuthenticatedUser> getAuthenticatedUser() {
    return Optional.empty();
  }
}
