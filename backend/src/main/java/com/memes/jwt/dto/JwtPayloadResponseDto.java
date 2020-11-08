package com.memes.jwt.dto;

import lombok.Data;

@Data
public class JwtPayloadResponseDto {
  private String accessToken;
  private Long iat;
  private Long exp;
  private String sub;
}
