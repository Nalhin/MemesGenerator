package com.memes.shared.advice;

import com.memes.shared.dto.ValidationExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class ControllerExceptionHandler {
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  ValidationExceptionDto handleMethodArgumentNotValidException(
      MethodArgumentNotValidException error) {

    return ValidationExceptionDto.from(error.getBindingResult().getFieldErrors());
  }
}
