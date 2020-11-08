package com.memes.comment.dto;

import com.memes.user.dto.UserResponseDto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CommentResponseDto {
  private Long id;
  private UserResponseDto author;
  private LocalDate created;
  private String content;
}
