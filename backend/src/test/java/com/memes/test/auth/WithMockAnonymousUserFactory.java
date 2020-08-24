package com.memes.test.auth;

import com.memes.auth.models.AnonymousUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockAnonymousUserFactory
        implements WithSecurityContextFactory<WithMockAnonymousUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockAnonymousUser anonymousUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication auth = new AnonymousAuthenticationToken("key",  new AnonymousUser(), AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));
        context.setAuthentication(auth);
        return context;
    }
}
