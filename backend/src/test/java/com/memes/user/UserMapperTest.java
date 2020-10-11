package com.memes.user;

import com.memes.user.dto.UserResponseDto;
import com.memes.user.test.UserTestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserMapperTest {

  private final UserMapper userMapper = new UserMapperImpl();

  @Nested
  class UserToUserResponseDto {

    @Test
    @DisplayName("Should map User to UserResponseDto and return result")
    void mapsCorrectly() {
      User user = UserTestBuilder.user().build();

      UserResponseDto actualResult = userMapper.userToUserResponseDto(user);

      assertThat(actualResult)
          .extracting("username", "email", "id")
          .containsExactly(user.getUsername(), user.getEmail(), user.getId());
    }
  }

  @Nested
  class usersToUserResponseDtoList {

    @Test
    @DisplayName("Should map Users List to UsersResponseDto List and return result")
    void mapsCorrectly() {
      List<User> users = UserTestBuilder.users(4);

      List<UserResponseDto> actualResult = userMapper.usersToUserResponseDtoList(users);

      assertThat(actualResult)
          .hasSize(4)
          .element(2)
          .extracting("username", "email", "id")
          .containsExactly(
              users.get(2).getUsername(), users.get(2).getEmail(), users.get(2).getId());
    }
  }
}
