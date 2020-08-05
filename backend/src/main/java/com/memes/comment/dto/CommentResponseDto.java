package com.memes.comment.dto;

import com.memes.user.dto.UserResponseDto;
import lombok.Data;

import java.util.Date;

@Data
public class CommentResponseDto {
    private Long id;
    private UserResponseDto author;
    private Date created;
    private String content;
}
