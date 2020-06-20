package com.memes.auth.dto;

import lombok.Data;

@Data
public class SignUpUserDto {
  private String email;
  private String username;
  private String password;
}
