package com.memes.config;

import com.memes.security.SecurityContextFacade;
import com.memes.security.SecurityContextFacadeTestImpl;
import com.memes.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

import static com.memes.security.test.SecurityUserTestFactory.authentication;
import static com.memes.test.utils.TestClockUtils.testClock;
import static com.memes.user.test.UserTestFactory.user;
import static org.assertj.core.api.Assertions.assertThat;

class JpaAuditingConfigurationTest {

  private SecurityContextFacade securityContextFacade;
  private final Clock clock = testClock();

  private JpaAuditingConfiguration jpaAuditingConfiguration;

  @BeforeEach
  void setUp() {
    securityContextFacade = new SecurityContextFacadeTestImpl();
    jpaAuditingConfiguration = new JpaAuditingConfiguration(securityContextFacade, clock);
  }

  @Nested
  @DisplayName("getCurrentAuditor()")
  class GetCurrentAuditor {

    @Test
    @DisplayName(
        "Should return an optional with authenticated user when user is present in context")
    void returnsAppUser() {
      User expectedAuditor = user().build();
      securityContextFacade.setAuthentication(authentication(expectedAuditor));

      Optional<User> actualAuditor = jpaAuditingConfiguration.getCurrentAuditor();

      assertThat(actualAuditor).contains(expectedAuditor);
    }

    @Test
    @DisplayName("Should return an empty optional when user is not present")
    void returnsEmpty() {

      Optional<User> actualAuditor = jpaAuditingConfiguration.getCurrentAuditor();

      assertThat(actualAuditor).isEmpty();
    }
  }

  @Nested
  @DisplayName("getNow()")
  class GetNow {

    @Test
    @DisplayName("Should return current date")
    void returnsCurrentDate() {

      Optional<TemporalAccessor> actualTime = jpaAuditingConfiguration.getNow();

      assertThat(actualTime).contains(LocalDate.now(clock));
    }
  }
}
