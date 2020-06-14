package com.memes.meme.dto;

import com.memes.template.dto.TemplateResponseDto;
import com.memes.user.dto.UserResponseDto;
import lombok.Data;

import java.util.Date;

@Data
public class MemeResponseDto {
  private Long id;
  private UserResponseDto author;
  private Date created;
  private String url;
  private TemplateResponseDto template;
}
