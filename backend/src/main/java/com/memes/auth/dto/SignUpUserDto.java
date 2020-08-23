package com.memes.auth.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
public class SignUpUserDto {

  @NotNull @Email private String email;

  @NotNull private String username;

  @NotNull
  @Size(min = 6)
  private String password;
}
