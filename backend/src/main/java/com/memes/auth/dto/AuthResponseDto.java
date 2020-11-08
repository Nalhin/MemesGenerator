package com.memes.auth.dto;

import com.memes.jwt.model.JwtPayload;
import com.memes.user.dto.UserResponseDto;
import lombok.Data;

@Data
public class AuthResponseDto {
  private JwtPayload payload;
  private UserResponseDto user;
}
