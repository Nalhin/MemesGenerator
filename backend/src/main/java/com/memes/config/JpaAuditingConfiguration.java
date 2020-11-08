package com.memes.config;

import com.memes.security.SecurityContextFacade;
import com.memes.security.model.AppUser;
import com.memes.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class JpaAuditingConfiguration implements AuditorAware<User>, DateTimeProvider {

  private final SecurityContextFacade securityContextFacade;
  private final Clock clock;

  @Override
  public Optional<User> getCurrentAuditor() {
    AppUser user = securityContextFacade.getAppUser();
    return user.getPresentUser();
  }

  @Override
  public Optional<TemporalAccessor> getNow() {
    return Optional.of(LocalDate.now(clock));
  }
}
