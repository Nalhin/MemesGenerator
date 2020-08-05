package com.memes.user;

import com.memes.user.dto.UserResponseDto;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

  private final UserMapper userMapper = new UserMapperImpl();

  @Test
  void userToUserResponseDto_FilledUser_ReturnsStrippedDto() {
    User user = new EasyRandom().nextObject(User.class);

    UserResponseDto result = userMapper.userToUserResponseDto(user);

    assertAll(
        () -> assertEquals(user.getUsername(), result.getUsername()),
        () -> assertEquals(user.getEmail(), result.getEmail()));
  }

  @Test
  void usersToUserResponseDtoList_FilledUserList_ReturnsUserResponseDtoList() {
    List<User> users = new EasyRandom().objects(User.class, 4).collect(Collectors.toList());

    List<UserResponseDto> result = userMapper.usersToUserResponseDtoList(users);

    assertAll(
        () -> assertEquals(users.size(), result.size()),
        () -> assertEquals(users.get(1).getUsername(), result.get(1).getUsername()),
        () -> assertEquals(users.get(2).getEmail(), result.get(2).getEmail()));
  }
}
