package com.memes.shared;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UnauthorizedException {
  private String message;

  @ApiModelProperty(value = "401", example = "401")
  private int status;
}
