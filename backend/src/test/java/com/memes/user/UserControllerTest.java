package com.memes.user;

import com.memes.auth.AuthUser;
import com.memes.shared.utils.CustomModelMapper;
import com.memes.user.dto.UserResponseDto;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

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
    userController = new UserController(userService, new CustomModelMapper());
    user = new EasyRandom().nextObject(User.class);
  }

  @Test
  void getAll_UsersPresent_ReturnsSameSize() {
    List<User> mockUsers = Arrays.asList(user, user);
    when(userService.findAll()).thenReturn(mockUsers);

    ResponseEntity<List<UserResponseDto>> result = userController.getAll();

    assertNotNull(result.getBody());
    assertEquals(mockUsers.size(), result.getBody().size());
  }

  @Test
  void me_UserPresent_ReturnsUserResponse() {
    AuthUser mockUser = mock(AuthUser.class);
    when(mockUser.getUser()).thenReturn(user);

    ResponseEntity<UserResponseDto> result = userController.me(mockUser);

    assertNotNull(result.getBody());
    assertEquals(user.getUsername(), result.getBody().getUsername());
  }
}
