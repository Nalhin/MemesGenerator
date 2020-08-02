package com.memes.auth;

import com.memes.user.User;
import com.memes.user.UserRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
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
    User mockUser = new EasyRandom().nextObject(User.class);
    when(userRepository.findOneByUsername(mockUser.getUsername()))
        .thenReturn(Optional.of(mockUser));

    UserDetails result = authUserDetailsService.loadUserByUsername(mockUser.getUsername());

    assertEquals(mockUser.getUsername(), result.getUsername());
  }

  @Test
  void loadUserByUsername_UserNotFound_ThrowsUsernameNotFoundException() {
    when(userRepository.findOneByUsername(anyString())).thenReturn(Optional.empty());

    assertThrows(
        UsernameNotFoundException.class,
        () -> authUserDetailsService.loadUserByUsername(""));
  }
}
