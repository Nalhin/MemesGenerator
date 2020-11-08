package com.memes.jwt.test;

import com.github.javafaker.Faker;
import com.memes.jwt.model.JwtPayload;

public class JwtTestFactory {
  private static final Faker faker = new Faker();

  public static JwtPayload.JwtPayloadBuilder jwtPayload() {
    return JwtPayload.builder()
        .sub(faker.name().username())
        .accessToken(faker.internet().uuid())
        .iat(faker.random().nextLong())
        .exp(faker.random().nextLong());
  }
}
