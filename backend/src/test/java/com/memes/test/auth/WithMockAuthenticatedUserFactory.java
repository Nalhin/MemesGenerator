package com.memes.test.auth;

import com.memes.auth.test.AuthTestBuilder;
import com.memes.user.User;
import com.memes.user.test.UserTestBuilder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockAuthenticatedUserFactory
    implements WithSecurityContextFactory<WithMockAuthenticatedUser> {
  @Override
  public SecurityContext createSecurityContext(WithMockAuthenticatedUser authenticatedUser) {
    User user =
        UserTestBuilder.user()
            .email(authenticatedUser.email())
            .username(authenticatedUser.username())
            .id(authenticatedUser.id())
            .build();
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(AuthTestBuilder.authentication(user));
    return context;
  }
}
