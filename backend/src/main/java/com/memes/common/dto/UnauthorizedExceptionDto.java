package com.memes.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UnauthorizedExceptionDto {
  private String message;

  @ApiModelProperty(value = "401", example = "401")
  private int status;
}
