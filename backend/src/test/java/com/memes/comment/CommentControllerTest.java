package com.memes.comment;

import com.memes.auth.models.AuthenticatedUser;
import com.memes.comment.dto.CommentResponseDto;
import com.memes.comment.dto.SaveCommentDto;
import com.memes.test.auth.WithMockAnonymousUser;
import com.memes.test.auth.WithMockAuthenticatedUser;
import com.memes.test.utils.MockSecurityConfig;
import com.memes.user.UserMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.memes.test.matchers.ResponseBodyMatchers.responseBody;
import static com.memes.test.matchers.ResponseHeaderMatchers.responseHeader;
import static com.memes.test.utils.RequestUtils.asJSON;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {CommentController.class})
@Import({UserMapperImpl.class, CommentMapperImpl.class})
public class CommentControllerTest extends MockSecurityConfig {

  @Autowired private MockMvc mockMvc;

  @Autowired private CommentMapper commentMapper;

  @MockBean private CommentService commentService;

  @Test
  void getCommentById_CommentWasFound_Returns200AndCommentResponse() throws Exception {
    Comment comment = CommentTestBuilder.commentWithAuthor().build();
    CommentResponseDto expected = commentMapper.commentToCommentResponseDto(comment);
    when(commentService.getOneById(comment.getId())).thenReturn(comment);

    mockMvc
        .perform(get("/comments/" + comment.getId()).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(responseBody().containsObjectAsJson(expected, CommentResponseDto.class));

    verify(commentService, times(1)).getOneById(comment.getId());
  }

  @Test
  @WithMockAuthenticatedUser()
  void saveComment_AuthenticatedUser_Returns200AndUserResponse() throws Exception {
    SaveCommentDto saveCommentDto = CommentTestBuilder.saveCommentDto().build();
    Comment savedComment = CommentTestBuilder.commentWithAuthor().build();
    CommentResponseDto expected = commentMapper.commentToCommentResponseDto(savedComment);
    when(commentService.saveComment(any(Comment.class), any(AuthenticatedUser.class)))
        .thenReturn(savedComment);

    mockMvc
        .perform(
            post("/comments")
                .content(asJSON(saveCommentDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isCreated())
        .andExpect(responseBody().containsObjectAsJson(expected, CommentResponseDto.class))
        .andExpect(responseHeader().containsLocation("/comments/" + expected.getId()));

    verify(commentService, times(1)).saveComment(any(Comment.class), any(AuthenticatedUser.class));
  }

  @Test
  @WithMockAnonymousUser()
  void saveComment_AnonymousUser_Returns403() throws Exception {
    SaveCommentDto saveCommentDto = CommentTestBuilder.saveCommentDto().build();

    mockMvc
        .perform(
            post("/comments")
                .content(asJSON(saveCommentDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isForbidden());

    verify(commentService, times(0)).saveComment(any(Comment.class), any(AuthenticatedUser.class));
  }

  @Test
  @WithMockAuthenticatedUser()
  void saveComment_InvalidInput_Returns400() throws Exception {
    SaveCommentDto saveCommentDto = SaveCommentDto.builder().build();

    mockMvc
        .perform(
            post("/comments")
                .content(asJSON(saveCommentDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isBadRequest())
        .andExpect(responseBody().containsValidationErrors("content"));

    verify(commentService, times(0)).saveComment(any(Comment.class), any(AuthenticatedUser.class));
  }
}
