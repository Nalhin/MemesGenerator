package com.memes.test.auth;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockAuthenticatedUserFactory.class)
public @interface WithMockAuthenticatedUser {

    String username() default "username";

    long id() default 1;

    String email() default "email@email.com";
}
