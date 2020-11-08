package com.memes.comment;

import com.memes.comment.dto.CommentResponseDto;
import com.memes.comment.dto.SaveCommentDto;
import com.memes.comment.test.CommentTestFactory;
import com.memes.test.auth.WithMockAnonymousUser;
import com.memes.test.auth.WithMockAuthenticatedUser;
import com.memes.test.utils.MockSecurityConfig;
import com.memes.user.UserMapperImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.memes.comment.test.CommentTestFactory.commentWithAuthor;
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

  @AfterEach
  public void afterEach() {
    reset(commentService);
  }

  @Nested
  class GetCommentById {

    @Test
    @DisplayName("Should return OK (200) status code and CommentResponseDto")
    void returnsFoundComment() throws Exception {
      Comment comment = commentWithAuthor().build();
      when(commentService.getOneById(comment.getId())).thenReturn(comment);

      mockMvc
          .perform(
              get("/comments/" + comment.getId()).contentType(MediaType.APPLICATION_JSON_VALUE))
          .andExpect(status().isOk())
          .andExpect(
              responseBody()
                  .containsObjectAsJson(
                      commentMapper.commentToCommentResponseDto(comment),
                      CommentResponseDto.class));

      verify(commentService, times(1)).getOneById(comment.getId());
    }
  }

  @Nested
  class SaveComment {

    @Test
    @WithMockAuthenticatedUser
    @DisplayName("Should return CREATED (201) status code and CommentResponseDto")
    void returns201IfSuccessful() throws Exception {
      Comment savedComment = commentWithAuthor().build();
      when(commentService.saveComment(any())).thenReturn(savedComment);

      mockMvc
          .perform(
              post("/comments")
                  .content(asJSON(CommentTestFactory.saveCommentDto().build()))
                  .contentType(MediaType.APPLICATION_JSON_VALUE))
          .andExpect(status().isCreated())
          .andExpect(
              responseBody()
                  .containsObjectAsJson(
                      commentMapper.commentToCommentResponseDto(savedComment),
                      CommentResponseDto.class))
          .andExpect(responseHeader().containsLocation("/comments/" + savedComment.getId()));

      verify(commentService, times(1)).saveComment(any());
    }

    @Test
    @WithMockAnonymousUser()
    @DisplayName("Should return FORBIDDEN (403) status code when user is anonymous")
    void returns403IfAnonymous() throws Exception {
      SaveCommentDto saveCommentDto = CommentTestFactory.saveCommentDto().build();

      mockMvc
          .perform(
              post("/comments")
                  .content(asJSON(saveCommentDto))
                  .contentType(MediaType.APPLICATION_JSON_VALUE))
          .andExpect(status().isForbidden());

      verify(commentService, times(0)).saveComment(any());
    }

    @Test
    @DisplayName("Should return BAD_REQUEST (400) status code when input is invalid")
    void returns400IfInvalidInput() throws Exception {
      mockMvc
          .perform(
              post("/comments")
                  .content(asJSON(new SaveCommentDto()))
                  .contentType(MediaType.APPLICATION_JSON_VALUE))
          .andExpect(status().isBadRequest())
          .andExpect(responseBody().containsValidationErrors("content"));

      verify(commentService, times(0)).saveComment(any());
    }
  }
}
