package com.memes.user;

import com.memes.auth.AuthUser;
import com.memes.user.dto.UserResponseDto;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  @Mock private UserService userService;
  private UserController userController;
  private User user;

  @BeforeEach
  void setUp() {
    userController = new UserController(userService, new ModelMapper());
    user = new EasyRandom().nextObject(User.class);
  }

  @Test
  void getAll() {
    List<User> mockUsers = Arrays.asList(user, user);
    when(userService.findAll()).thenReturn(mockUsers);

    List<UserResponseDto> result = userController.getAll();

    assertEquals(mockUsers.size(), result.size());
  }

  @Test
  void me() {
    AuthUser mockUser = mock(AuthUser.class);
    when(mockUser.getUser()).thenReturn(user);

    UserResponseDto result = userController.me(mockUser);

    assertEquals(user.getUsername(), result.getUsername());
  }
}
