package com.memes.auth.dto;

import com.memes.user.dto.UserResponseDto;
import lombok.Data;

@Data
public class AuthResponseDto {
  private String token;
  private UserResponseDto user;
}
