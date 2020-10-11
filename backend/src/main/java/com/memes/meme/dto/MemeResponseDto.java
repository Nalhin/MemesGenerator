package com.memes.meme.dto;

import com.memes.template.dto.TemplateResponseDto;
import com.memes.user.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemeResponseDto {
  private Long id;
  private UserResponseDto author;
  private Date created;
  private String url;
  private TemplateResponseDto template;
}
