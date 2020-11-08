package com.memes.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtPayload {
  private String accessToken;
  private Long iat;
  private Long exp;
  private String sub;
}
