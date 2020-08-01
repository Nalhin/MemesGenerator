package com.memes.user;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock private UserRepository userRepository;

  @InjectMocks
  private UserService userService;
  private User user;

  @BeforeEach
  void setUp() {
    user = new EasyRandom().nextObject(User.class);
  }

  @Test
  void findAll_UsersPresent_ReturnsUsers() {
    List<User> mockUsers = Arrays.asList(user, user);
    when(userRepository.findAll()).thenReturn(mockUsers);

    List<User> result = userService.findAll();

    assertEquals(mockUsers, result);
  }

  @Test
  void findOneByUsername_UserFound_ReturnsUser() {
    when(userRepository.findOneByUsername(user.getUsername())).thenReturn(Optional.of(user));

    Optional<User> result = userService.findOneByUsername(user.getUsername());

    assertEquals(Optional.of(user), result);
  }

  @Test
  void save_OperationSuccessful_SaveCalled() {
    userService.save(user);

    verify(userRepository).save(user);
  }
}
