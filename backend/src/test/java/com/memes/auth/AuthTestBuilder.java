package com.memes.auth;

import com.github.javafaker.Faker;
import com.memes.auth.dto.LoginUserDto;
import com.memes.auth.dto.SignUpUserDto;
import com.memes.user.User;
import com.memes.user.UserTestBuilder;
import org.springframework.data.util.Pair;

public class AuthTestBuilder {

  private static final Faker faker = new Faker();

  public static LoginUserDto.LoginUserDtoBuilder loginUserDto() {
    return LoginUserDto.builder()
        .username(faker.name().username())
        .password(faker.internet().password());
  }

  public static Pair<User, String> authPair() {
    return Pair.of(UserTestBuilder.user().build(), faker.lorem().word());
  }

  public static SignUpUserDto.SignUpUserDtoBuilder signUpUserDto() {
    return SignUpUserDto.builder()
        .username(faker.name().username())
        .password(faker.internet().password())
        .email(faker.internet().emailAddress());
  }
}
