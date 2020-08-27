package com.memes.test.auth;

import com.memes.auth.models.AuthenticatedUser;
import com.memes.user.User;
import org.jeasy.random.EasyRandom;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Collections;

public class WithMockAuthenticatedUserFactory
        implements WithSecurityContextFactory<WithMockAuthenticatedUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockAuthenticatedUser authenticatedUser) {
        User user = new EasyRandom().nextObject(User.class);
        user.setEmail(authenticatedUser.email());
        user.setUsername(authenticatedUser.username());
        user.setId(authenticatedUser.id());
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        AuthenticatedUser authUser = AuthenticatedUser.authBuilder()
                .user(user).accountNonExpired(true).accountNonLocked(true)
                .authorities(Collections.emptyList())
                .credentialsNonExpired(true).password(user.getPassword())
                .enabled(true).username(user.getUsername()).build();
        Authentication auth = new UsernamePasswordAuthenticationToken(authUser, user.getPassword(),Collections.emptyList());
        context.setAuthentication(auth);
        return context;
    }

}
