package com.memes.comment;

import com.memes.comment.dto.CommentResponseDto;
import com.memes.comment.dto.SaveCommentDto;
import com.memes.user.UserMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = UserMapper.class,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CommentMapper {

  CommentResponseDto commentToCommentResponseDto(Comment comment);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "created", ignore = true)
  @Mapping(target = "author", ignore = true)
  Comment saveCommentDtoToComment(SaveCommentDto saveCommentDto);
}
