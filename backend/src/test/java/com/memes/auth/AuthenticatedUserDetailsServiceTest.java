package com.memes.auth;

import com.memes.user.User;
import com.memes.user.UserRepository;
import com.memes.user.UserTestBuilder;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticatedUserDetailsServiceTest {

  @Mock private UserRepository userRepository;
  @InjectMocks private AuthUserDetailsService authUserDetailsService;

  @Test
  void loadUserByUsername_UserFound_ReturnsUser() {
    User user = UserTestBuilder.user().build();
    when(userRepository.findOneByUsername(user.getUsername()))
        .thenReturn(Optional.of(user));

    UserDetails result = authUserDetailsService.loadUserByUsername(user.getUsername());

    assertEquals(user.getUsername(), result.getUsername());
  }

  @Test
  void loadUserByUsername_UserNotFound_ThrowsUsernameNotFoundException() {
    when(userRepository.findOneByUsername(anyString())).thenReturn(Optional.empty());

    assertThrows(
        UsernameNotFoundException.class,
        () -> authUserDetailsService.loadUserByUsername(""));
  }
}
