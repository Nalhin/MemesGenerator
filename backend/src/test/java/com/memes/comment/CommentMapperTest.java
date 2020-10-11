package com.memes.comment;

import com.memes.comment.dto.CommentResponseDto;
import com.memes.comment.dto.SaveCommentDto;
import com.memes.comment.test.CommentTestBuilder;
import com.memes.user.UserMapperImpl;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentMapperTest {

  private final CommentMapper commentMapper = new CommentMapperImpl(new UserMapperImpl());

  @Nested
  class CommentToCommentResponseDto {

    @Test
    @DisplayName("Should map Comment to CommentResponseDto")
    void mapsToDto() {
      Comment comment = CommentTestBuilder.commentWithAuthor().build();

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
  }

  @Nested
  class SaveCommentDtoToComment {

    @Test
    @DisplayName("Should map SaveCommentDto to Comment")
    void mapsFromDto() {
      SaveCommentDto providedDto = CommentTestBuilder.saveCommentDto().build();

      Comment actualComment = commentMapper.saveCommentDtoToComment(providedDto);

      assertThat(actualComment).extracting("content").isEqualTo(providedDto.getContent());
    }
  }
}
