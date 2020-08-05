package com.memes.comment;

import com.memes.comment.dto.CommentResponseDto;
import com.memes.comment.dto.SaveCommentDto;
import com.memes.user.UserMapperImpl;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentMapperTest {

  private final CommentMapper commentMapper = new CommentMapperImpl(new UserMapperImpl());

  @Test
  void commentToCommentResponseDto_FilledComment_ReturnsCommentResponseDto() {
    Comment comment = new EasyRandom().nextObject(Comment.class);

    CommentResponseDto result = commentMapper.commentToCommentResponseDto(comment);

    assertAll(
        () -> assertEquals(comment.getCreated(), result.getCreated()),
        () -> assertEquals(comment.getContent(), result.getContent()),
        () -> assertEquals(comment.getId(), result.getId()),
        () -> assertEquals(comment.getAuthor().getEmail(), result.getAuthor().getEmail()));
  }

  @Test
  void saveCommentDtoToComment_FilledSaveCommentDto_ReturnsComment() {
    SaveCommentDto saveCommentDto = new EasyRandom().nextObject(SaveCommentDto.class);

    Comment result = commentMapper.saveCommentDtoToComment(saveCommentDto);

    assertEquals(saveCommentDto.getContent(), result.getContent());
  }
}
