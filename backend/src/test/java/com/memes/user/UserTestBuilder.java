package com.memes.user;

import com.github.javafaker.Faker;
import com.memes.auth.models.AuthenticatedUser;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserTestBuilder {

  private static final Faker faker = new Faker();

  public static AuthenticatedUser.AuthenticatedUserBuilder authUser() {
    User user = user().build();

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

  public static User.UserBuilder user() {
    return User.builder()
        .email(faker.internet().emailAddress())
        .username(faker.name().username())
        .password(faker.internet().password())
        .id(faker.number().numberBetween(0, Long.MAX_VALUE));
  }

  public static List<User> users(int count) {
    return Stream.generate(() -> user().build()).limit(count).collect(Collectors.toList());
  }

  public static List<User> users(int count, User.UserBuilder userBuilder) {
    return Stream.generate(userBuilder::build).limit(count).collect(Collectors.toList());
  }
}
