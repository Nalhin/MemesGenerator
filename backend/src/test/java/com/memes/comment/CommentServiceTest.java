package com.memes.comment;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

  @Mock private CommentRepository commentRepository;
  @InjectMocks private CommentService commentService;

  private final EasyRandom random = new EasyRandom();

  @Test
  void getOneById_CommentFound_ReturnsComment() {
    Comment comment = random.nextObject(Comment.class);
    when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));

    Comment result = commentService.getOneById(comment.getId());

    assertEquals(comment, result);
  }

  @Test
  void getOneById_CommentNotFound_ThrowsException() {
    when(commentRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThrows(ResponseStatusException.class, () -> commentService.getOneById(1L));
  }

  @Test
  void saveComment_OperationSuccessful_ReturnsComment() {
    Comment comment = random.nextObject(Comment.class);
    when(commentRepository.save(any(Comment.class))).then(returnsFirstArg());

    Comment result = commentService.saveComment(comment);

    assertEquals(comment, result);
  }
}
