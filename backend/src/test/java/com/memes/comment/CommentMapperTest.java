package com.memes.comment;

import com.memes.comment.dto.CommentResponseDto;
import com.memes.comment.dto.SaveCommentDto;
import com.memes.user.UserMapperImpl;
import org.assertj.core.api.SoftAssertions;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentMapperTest {

  private final CommentMapper commentMapper = new CommentMapperImpl(new UserMapperImpl());

  @Test
  void commentToCommentResponseDto_FilledComment_ReturnsCommentResponseDto() {
    Comment comment = new EasyRandom().nextObject(Comment.class);

    CommentResponseDto result = commentMapper.commentToCommentResponseDto(comment);

    SoftAssertions.assertSoftly(
        softly -> {
          softly.assertThat(result.getCreated()).isEqualTo(comment.getCreated());
          softly.assertThat(result.getContent()).isEqualTo(comment.getContent());
          softly.assertThat(result.getId()).isEqualTo(comment.getId());
          softly
              .assertThat(result.getAuthor().getEmail())
              .isEqualTo(comment.getAuthor().getEmail());
        });
  }

  @Test
  void saveCommentDtoToComment_FilledSaveCommentDto_ReturnsComment() {
    SaveCommentDto saveCommentDto = new EasyRandom().nextObject(SaveCommentDto.class);

    Comment result = commentMapper.saveCommentDtoToComment(saveCommentDto);

    assertThat(saveCommentDto.getContent()).isEqualTo(result.getContent());
  }
}
