package com.memes.jwt;

import com.memes.jwt.dto.JwtPayloadResponseDto;
import com.memes.jwt.model.JwtPayload;
import com.memes.jwt.test.JwtTestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtMapperTest {

  private final JwtMapper mapper = new JwtMapperImpl();

  @Nested
  @DisplayName("jwtPayloadToResponse()")
  class JwtPayloadToResponse {

    @Test
    @DisplayName("Should map JwtPayload to JwtPayloadResponseDto")
    void mapsToResponse() {
      JwtPayload target = JwtTestFactory.jwtPayload().build();

      JwtPayloadResponseDto actualResult = mapper.jwtPayloadToResponse(target);

      assertThat(actualResult)
          .extracting("accessToken", "sub", "iat", "exp")
          .containsExactly(
              actualResult.getAccessToken(),
              actualResult.getSub(),
              actualResult.getIat(),
              actualResult.getExp());
    }
  }
}
