package com.memes.comment;

import com.memes.auth.test.AuthTestBuilder;
import com.memes.comment.test.CommentTestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

  @Mock private CommentRepository commentRepository;
  @InjectMocks private CommentService commentService;

  @Nested
  class GetOneById {

    @Test
    @DisplayName("Should return comment when found")
    void returnsComment() {
      Comment expectedComment = CommentTestBuilder.comment().build();
      when(commentRepository.findById(expectedComment.getId()))
          .thenReturn(Optional.of(expectedComment));

      Comment actualComment = commentService.getOneById(expectedComment.getId());

      assertThat(actualComment).isEqualTo(expectedComment);
    }

    @Test
    @DisplayName("Should throw exception")
    void throwsException() {
      when(commentRepository.findById(1L)).thenReturn(Optional.empty());

      assertThatThrownBy(() -> commentService.getOneById(1L))
          .isInstanceOf(ResponseStatusException.class);
    }
  }

  @Nested
  class Save {
    @Test
    @DisplayName("Should save and return saved comment")
    void returnsComment() {
      Comment expectedComment = CommentTestBuilder.comment().build();
      when(commentRepository.save(any(Comment.class))).then(returnsFirstArg());

      Comment actualComment =
          commentService.saveComment(expectedComment, AuthTestBuilder.authUser().build());

      assertThat(actualComment).isEqualTo(expectedComment);
    }
  }
}
