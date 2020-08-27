package com.memes.comment;

import com.memes.user.UserTestBuilder;
import org.jeasy.random.EasyRandom;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

  @Mock private CommentRepository commentRepository;
  @InjectMocks private CommentService commentService;

  @Test
  void getOneById_CommentFound_ReturnsComment() {
    Comment comment = new EasyRandom().nextObject(Comment.class);
    when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

    Comment result = commentService.getOneById(comment.getId());

    assertThat(result).isEqualTo(comment);
  }

  @Test
  void getOneById_CommentNotFound_ThrowsException() {
    when(commentRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThatThrownBy(() -> commentService.getOneById(1L))
        .isInstanceOf(ResponseStatusException.class);
  }

  @Test
  void saveComment_OperationSuccessful_ReturnsComment() {
    Comment comment = CommentTestBuilder.comment().build();
    when(commentRepository.save(any(Comment.class))).then(returnsFirstArg());

    Comment result = commentService.saveComment(comment, UserTestBuilder.authUser().build());

    assertThat(result).isEqualTo(comment);
  }
}
