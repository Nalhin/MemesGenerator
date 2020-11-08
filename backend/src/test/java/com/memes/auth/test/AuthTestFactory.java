package com.memes.auth.test;

import com.github.javafaker.Faker;
import com.memes.auth.dto.LoginUserDto;
import com.memes.auth.dto.SignUpUserDto;
import com.memes.jwt.model.JwtPayload;
import com.memes.jwt.test.JwtTestFactory;
import com.memes.security.model.AuthenticatedUser;
import com.memes.user.User;
import com.memes.user.test.UserTestFactory;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.Collections;

public class AuthTestFactory {

  private static final Faker faker = new Faker();

  public static LoginUserDto.LoginUserDtoBuilder loginUserDto() {
    return LoginUserDto.builder()
        .username(faker.name().username())
        .password(faker.internet().password());
  }

  public static Pair<User, JwtPayload> authPair() {
    return Pair.of(UserTestFactory.user().build(), JwtTestFactory.jwtPayload().build());
  }

  public static SignUpUserDto.SignUpUserDtoBuilder signUpUserDto() {
    return SignUpUserDto.builder()
        .username(faker.name().username())
        .password(faker.internet().password())
        .email(faker.internet().emailAddress());
  }

  public static AuthenticatedUser.AuthenticatedUserBuilder authUser(User user) {

    return AuthenticatedUser.authBuilder()
        .username(user.getUsername())
        .user(user)
        .password(user.getPassword())
        .accountNonExpired(true)
        .accountNonLocked(true)
        .credentialsNonExpired(true)
        .enabled(true)
        .authorities(Collections.emptyList());
  }

  public static AuthenticatedUser.AuthenticatedUserBuilder authUser() {
    return authUser(UserTestFactory.user().build());
  }
}
