package com.memes.auth;

import com.memes.auth.dto.AuthResponseDto;
import com.memes.auth.dto.SignUpUserDto;
import com.memes.auth.test.AuthTestFactory;
import com.memes.jwt.model.JwtPayload;
import com.memes.user.User;
import com.memes.user.UserMapperImpl;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

class AuthMapperTest {

  private final AuthMapper authMapper = new AuthMapperImpl(new UserMapperImpl());

  @Nested
  class AuthPairToAuthResponseDto {

    @Test
    @DisplayName("Should map AuthPair to AuthResponseDto")
    void mapsToDto() {
      Pair<User, JwtPayload> providedAuthPair = AuthTestFactory.authPair();
      AuthResponseDto actualResult = authMapper.authPairToResponse(providedAuthPair);

      SoftAssertions.assertSoftly(
          softly -> {
            softly
                .assertThat(actualResult.getUser().getUsername())
                .isEqualTo(providedAuthPair.getFirst().getUsername());
            softly
                .assertThat(actualResult.getUser().getEmail())
                .isEqualTo(providedAuthPair.getFirst().getEmail());
            softly.assertThat(actualResult.getPayload()).isEqualTo(providedAuthPair.getSecond());
          });
    }
  }

  @Nested
  class SignUpUserDtoToUser {

    @Test
    @DisplayName("Should map SignUpUserDto to User")
    void mapsToEntity() {
      SignUpUserDto providedSignUpUserDto = AuthTestFactory.signUpUserDto().build();

      User actualResult = authMapper.signUpUserDtoToUser(providedSignUpUserDto);

      SoftAssertions.assertSoftly(
          softly -> {
            softly
                .assertThat(actualResult.getPassword())
                .isEqualTo(providedSignUpUserDto.getPassword());
            softly
                .assertThat(actualResult.getUsername())
                .isEqualTo(providedSignUpUserDto.getUsername());
            softly.assertThat(actualResult.getEmail()).isEqualTo(providedSignUpUserDto.getEmail());
          });
    }
  }
}
