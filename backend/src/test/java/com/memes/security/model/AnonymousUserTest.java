package com.memes.security.model;

import com.memes.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

class AnonymousUserTest {

  @Nested
  @DisplayName("isAuthenticated()")
  class IsAuthenticated {

    @Test
    @DisplayName("Should return false")
    void returnsFalse() {
      AnonymousUser anonymousUser = new AnonymousUser();

      boolean actualResult = anonymousUser.isAuthenticated();

      assertThat(actualResult).isFalse();
    }
  }

  @Nested
  @DisplayName("ifAuthenticated()")
  class IfAuthenticated {

    @Test
    @DisplayName("Should not run")
    void doesNotRun() {
      AtomicInteger calls = new AtomicInteger();
      AnonymousUser anonymousUser = new AnonymousUser();

      anonymousUser.ifAuthenticated((d) -> calls.getAndIncrement());

      assertThat(calls.get()).isEqualTo(0);
    }
  }

  @Nested
  @DisplayName("ifAnonymous()")
  class IfAnonymous {

    @Test
    @DisplayName("Should execute and pass anonymous user as an argument")
    void runs() {
      AtomicReference<AnonymousUser> expectedUser = new AtomicReference<>();
      AnonymousUser anonymousUser = new AnonymousUser();

      anonymousUser.ifAnonymous(expectedUser::set);

      assertThat(expectedUser).hasValue(anonymousUser);
    }
  }

  @Nested
  @DisplayName("getPresentUser()")
  class GetPresentUser {

    @Test
    @DisplayName("Should return an empty optional")
    void returnsAnEmptyOptional() {
      AnonymousUser anonymousUser = new AnonymousUser();

      Optional<User> actualResult = anonymousUser.getPresentUser();

      assertThat(actualResult).isEmpty();
    }
  }
}
