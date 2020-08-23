package com.memes.auth;

import com.memes.auth.dto.AuthResponseDto;
import com.memes.auth.dto.SignUpUserDto;
import com.memes.user.User;
import com.memes.user.UserMapperImpl;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.util.Pair;

class AuthMapperTest {

  private final AuthMapper authMapper = new AuthMapperImpl(new UserMapperImpl());

  @Test
  void authPairToAuthResponseDto_UserAndTokenPresent_ReturnsAuthResponseDto() {
    Pair<User, String> auth = AuthTestBuilder.authPair();
    AuthResponseDto result = authMapper.authPairToUserResponseDto(auth);

    SoftAssertions.assertSoftly(
        softly -> {
          softly
              .assertThat(result.getUser().getUsername())
              .isEqualTo(auth.getFirst().getUsername());
          softly.assertThat(result.getUser().getEmail()).isEqualTo(auth.getFirst().getEmail());
          softly.assertThat(result.getToken()).isEqualTo(auth.getSecond());
        });
  }

  @Test
  void signUpUserDtoToUser() {
    SignUpUserDto signUpUserDto = AuthTestBuilder.signUpUserDto().build();
    User result = authMapper.signUpUserDtoToUser(signUpUserDto);

    SoftAssertions.assertSoftly(
        softly -> {
          softly.assertThat(result.getPassword()).isEqualTo(signUpUserDto.getPassword());
          softly.assertThat(result.getUsername()).isEqualTo(signUpUserDto.getUsername());
          softly.assertThat(result.getEmail()).isEqualTo(signUpUserDto.getEmail());
        });
  }
}
