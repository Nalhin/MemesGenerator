package com.memes.auth;

import com.memes.user.User;
import com.memes.user.UserRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthUserDetailsServiceTest {

  @Mock private UserRepository userRepository;

  private AuthUserDetailsService authUserDetailsService;

  @BeforeEach
  void setUp() {
    authUserDetailsService = new AuthUserDetailsService(userRepository);
  }

  @Test
  void loadUserByUsernameUserPresent() {
    User mockUser = new EasyRandom().nextObject(User.class);
    when(userRepository.findOneByUsername(mockUser.getUsername()))
        .thenReturn(Optional.of(mockUser));

    UserDetails result = authUserDetailsService.loadUserByUsername(mockUser.getUsername());

    assertEquals(mockUser.getUsername(), result.getUsername());
  }

  @Test
  void loadUserByUsernameUserNotFound() {
    when(userRepository.findOneByUsername(anyString())).thenReturn(Optional.empty());

    assertThrows(
        UsernameNotFoundException.class,
        () -> {
          authUserDetailsService.loadUserByUsername("");
        });
  }
}
