package com.memes.user;

import com.memes.user.dto.UserResponseDto;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class UserMapperTest {

  private final UserMapper userMapper = new UserMapperImpl();

  @Test
  void userToUserResponseDto_whenFilledUser_ReturnsStrippedDto() {
    User user = UserTestBuilder.user().build();

    UserResponseDto result = userMapper.userToUserResponseDto(user);

    SoftAssertions.assertSoftly(
        softly -> {
          softly.assertThat(result.getUsername()).isEqualTo(user.getUsername());
          softly.assertThat(result.getEmail()).isEqualTo(user.getEmail());
        });
  }

  @Test
  void usersToUserResponseDtoList_whenFilledUserList_ReturnsUserResponseDtoList() {
    List<User> users = UserTestBuilder.users(4);

    List<UserResponseDto> result = userMapper.usersToUserResponseDtoList(users);

    SoftAssertions.assertSoftly(
        softly -> {
          softly.assertThat(result).hasSize(4);
          softly.assertThat(result.get(1).getEmail()).isEqualTo(users.get(1).getEmail());
          softly.assertThat(result.get(2).getUsername()).isEqualTo(users.get(2).getUsername());
        });
  }
}
