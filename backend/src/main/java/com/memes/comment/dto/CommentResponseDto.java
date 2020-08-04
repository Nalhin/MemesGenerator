package com.memes.comment.dto;

import com.memes.user.User;
import lombok.Data;

import java.util.Date;

@Data
public class CommentResponseDto {
    private Long id;
    private User author;
    private Date created;
    private String content;
}
