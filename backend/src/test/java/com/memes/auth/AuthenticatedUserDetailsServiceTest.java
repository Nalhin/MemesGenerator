package com.memes.auth;

import com.memes.user.User;
import com.memes.user.UserRepository;
import com.memes.user.test.UserTestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticatedUserDetailsServiceTest {

  @Mock private UserRepository userRepository;
  @InjectMocks private AuthUserDetailsService authUserDetailsService;

  @Nested
  class LoadUserByUsername {

    @Test
    @DisplayName("Should throw UserNotFoundException when user is not found")
    void throwsUserNotFoundException(){
      when(userRepository.findOneByUsername("")).thenReturn(Optional.empty());

      assertThatThrownBy(() -> authUserDetailsService.loadUserByUsername(""))
              .isInstanceOf(UsernameNotFoundException.class);
    }

    @Test
    @DisplayName("Should return UserDetails when user is found")
    void returnUserDetails(){
      User user = UserTestBuilder.user().build();
      when(userRepository.findOneByUsername(user.getUsername())).thenReturn(Optional.of(user));

      UserDetails actualResult = authUserDetailsService.loadUserByUsername(user.getUsername());

      assertThat(actualResult.getUsername()).isEqualTo(user.getUsername());
    }
  }
}
