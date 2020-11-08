package com.memes.security.model;

import com.memes.user.User;

import java.util.Optional;
import java.util.function.Consumer;

public interface AppUser {
  boolean isAuthenticated();

  void ifAuthenticated(Consumer<AuthenticatedUser> userConsumer);

  void ifAnonymous(Consumer<AnonymousUser> userConsumer);

  Optional<User> getPresentUser();
}
