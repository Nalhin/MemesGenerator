package com.memes.auth.models;

import com.memes.user.User;

import java.util.Optional;

public interface AuthUser {
  boolean isAuthenticated();

  Optional<User> getUser();

  Optional<AuthenticatedUser> getAuthenticatedUser();
}
