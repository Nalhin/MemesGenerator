package com.memes.test.utils;

import com.memes.jwt.JwtService;
import com.memes.security.BearerHeaderTokenResolver;
import com.memes.security.SecurityContextFacadeImpl;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import springfox.documentation.swagger.web.SecurityConfiguration;

@Import(SecurityConfiguration.class)
public abstract class MockSecurityConfig {

  /** Mocked bean because it's a dependency of the SecurityConfiguration */
  @MockBean protected UserDetailsService userDetailsService;

  /** Mocked bean because it's a dependency of the SecurityConfiguration */
  @MockBean protected JwtService jwtService;

  /** Mocked bean because it's a dependency of the SecurityConfiguration */
  @MockBean
  SecurityContextFacadeImpl securityContextFacade;

  @MockBean
  BearerHeaderTokenResolver bearerTokenHeaderResolver;
}
