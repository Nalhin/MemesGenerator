package com.memes.auth;

import com.memes.auth.dto.AuthResponseDto;
import com.memes.auth.dto.SignUpUserDto;
import com.memes.user.User;
import com.memes.user.UserMapperImpl;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

import static org.junit.jupiter.api.Assertions.*;

class AuthMapperTest {

  private final AuthMapper authMapper = new AuthMapperImpl(new UserMapperImpl());

  @Test
  void authPairToAuthResponseDto_UserAndTokenPresent_ReturnsAuthResponseDto() {
    User user = new EasyRandom().nextObject(User.class);
    String token = new EasyRandom().nextObject(String.class);
    AuthResponseDto result = authMapper.authPairToUserResponseDto(Pair.of(user, token));

    assertAll(
        () -> assertEquals(user.getUsername(), result.getUser().getUsername()),
        () -> assertEquals(user.getEmail(), result.getUser().getEmail()),
        () -> assertEquals(token, result.getToken()));
  }

  @Test
  void signUpUserDtoToUser() {
    SignUpUserDto signUpUserDto = new EasyRandom().nextObject(SignUpUserDto.class);
    User result = authMapper.signUpUserDtoToUser(signUpUserDto);

    assertAll(
        () -> assertEquals(signUpUserDto.getPassword(), result.getPassword()),
        () -> assertEquals(signUpUserDto.getUsername(), result.getUsername()),
        () -> assertEquals(signUpUserDto.getEmail(), result.getEmail()));
  }
}
