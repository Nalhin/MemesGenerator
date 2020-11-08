package com.memes.test.auth;

import com.memes.user.User;
import com.memes.user.test.UserTestFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import static com.memes.security.test.SecurityUserTestFactory.authentication;

public class WithMockAuthenticatedUserFactory
    implements WithSecurityContextFactory<WithMockAuthenticatedUser> {
  @Override
  public SecurityContext createSecurityContext(WithMockAuthenticatedUser authenticatedUser) {
    User user =
        UserTestFactory.user()
            .email(authenticatedUser.email())
            .username(authenticatedUser.username())
            .id(authenticatedUser.id())
            .build();
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(authentication(user));
    return context;
  }
}
