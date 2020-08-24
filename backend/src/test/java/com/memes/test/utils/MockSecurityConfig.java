package com.memes.test.utils;

import com.memes.auth.JwtService;
import com.memes.auth.SecurityContextFacade;
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
  @MockBean SecurityContextFacade securityContextFacade;
}
