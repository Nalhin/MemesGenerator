package com.memes.security.model;

import com.memes.security.test.SecurityUserTestFactory;
import com.memes.user.User;
import com.memes.user.test.UserTestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

class AuthenticatedUserTest {

  @Nested
  @DisplayName("isAuthenticated()")
  class IsAuthenticated {

    @Test
    @DisplayName("Should return true")
    void returnsFalse() {
      AuthenticatedUser authenticatedUser = SecurityUserTestFactory.authenticatedUser().build();

      boolean actualResult = authenticatedUser.isAuthenticated();

      assertThat(actualResult).isTrue();
    }
  }

  @Nested
  @DisplayName("ifAnonymous()")
  class IfAnonymous {

    @Test
    @DisplayName("Should not run")
    void doesNotRun() {
      AtomicInteger calls = new AtomicInteger();
      AuthenticatedUser anonymousUser = SecurityUserTestFactory.authenticatedUser().build();

      anonymousUser.ifAnonymous((d) -> calls.getAndIncrement());

      assertThat(calls.get()).isEqualTo(0);
    }
  }

  @Nested
  @DisplayName("ifAuthenticated()")
  class IfAuthenticated {

    @Test
    @DisplayName("Should execute and pass authenticated user as an argument")
    void runs() {
      AtomicReference<AuthenticatedUser> expectedUser = new AtomicReference<>();
      AuthenticatedUser authenticated = SecurityUserTestFactory.authenticatedUser().build();

      authenticated.ifAuthenticated(expectedUser::set);

      assertThat(expectedUser).hasValue(authenticated);
    }
  }

  @Nested
  @DisplayName("getPresentUser()")
  class GetPresentUser {

    @Test
    @DisplayName("Should return an optional with user")
    void returnsAnEmptyOptional() {
      User expectedUser = UserTestFactory.user().build();
      AuthenticatedUser authenticatedUser =
          SecurityUserTestFactory.authenticatedUser(expectedUser).build();

      Optional<User> actualResult = authenticatedUser.getPresentUser();

      assertThat(actualResult).contains(expectedUser);
    }
  }
}
