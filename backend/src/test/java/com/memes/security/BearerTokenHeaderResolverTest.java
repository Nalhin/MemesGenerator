package com.memes.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class BearerTokenHeaderResolverTest {

  private final BearerHeaderTokenResolver bearerTokenHeaderResolver =
      new BearerHeaderTokenResolver();

  @Nested
  @DisplayName("resolveTokenFromHeader()")
  class ResolveTokenFromHeader {

    @Test
    @DisplayName("Should return an empty optional when header does not contain 'Bearer '")
    void noBearer() {
      String providedHeader = "header";

      Optional<String> actualResult =
          bearerTokenHeaderResolver.resolveTokenFromHeader(providedHeader);

      assertThat(actualResult).isEmpty();
    }

    @Test
    @DisplayName("Should return an empty optional when header does not start with 'Bearer '")
    void doesntStartWithBearer() {
      String providedHeader = "header Bearer ";

      Optional<String> actualResult =
          bearerTokenHeaderResolver.resolveTokenFromHeader(providedHeader);

      assertThat(actualResult).isEmpty();
    }

    @Test
    @DisplayName("Should return token resolved from header")
    void returnsToken() {
      String providedHeader = "Bearer token";

      Optional<String> actualResult =
          bearerTokenHeaderResolver.resolveTokenFromHeader(providedHeader);

      assertThat(actualResult).contains("token");
    }
  }
}
