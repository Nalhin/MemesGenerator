package com.memes.user.dto;

import lombok.Data;

@Data
public class UserResponseDto {
  private String email;
  private String username;
  private Long id;
}
