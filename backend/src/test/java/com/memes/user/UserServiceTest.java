package com.memes.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock private UserRepository userRepository;

  @InjectMocks private UserService userService;

  @Test
  void findAll_UsersPresent_ReturnsUsers() {
    List<User> mockUsers = UserTestBuilder.users(4);
    when(userRepository.findAll()).thenReturn(mockUsers);

    List<User> result = userService.findAll();

    assertThat(mockUsers).isEqualTo(result);
  }

  @Test
  void findOneByUsername_UserFound_ReturnsUser() {
    User user = UserTestBuilder.user().build();
    when(userRepository.findOneByUsername(user.getUsername())).thenReturn(Optional.of(user));

    Optional<User> result = userService.findOneByUsername(user.getUsername());

    assertThat(Optional.of(user)).isEqualTo(result);
  }

  @Test
  void save_UserSaved_SaveMethodCalled() {
    User user = UserTestBuilder.user().build();

    userService.save(user);

    verify(userRepository).save(user);
  }
}
