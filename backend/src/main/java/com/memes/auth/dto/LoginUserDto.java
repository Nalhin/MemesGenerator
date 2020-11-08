package com.memes.auth.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class LoginUserDto {

  @NotNull private String username;

  @NotNull private String password;
}
