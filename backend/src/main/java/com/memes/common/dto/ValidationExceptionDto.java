package com.memes.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ValidationExceptionDto {

  private List<FieldValidationErrorDto> errors;

  @ApiModelProperty(value = "400", example = "400")
  private int status;

  public static ValidationExceptionDto from(List<FieldError> validationErrors) {
    List<FieldValidationErrorDto> newErrors =
        validationErrors.stream()
            .map(e -> new FieldValidationErrorDto(e.getField(), e.getDefaultMessage()))
            .collect(Collectors.toList());
    ValidationExceptionDto exceptionDto = new ValidationExceptionDto();
    exceptionDto.setErrors(newErrors);
    return exceptionDto;
  }

  @AllArgsConstructor
  @Data
  static private class FieldValidationErrorDto {
    private final String field;
    private final String message;
  }
}
