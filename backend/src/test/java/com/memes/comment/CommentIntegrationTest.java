package com.memes.comment;

import com.memes.comment.dto.CommentResponseDto;
import com.memes.comment.dto.SaveCommentDto;
import com.memes.comment.test.CommentTestBuilder;
import com.memes.user.User;
import com.memes.user.UserRepository;
import com.memes.user.test.UserTestBuilder;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.memes.test.matchers.ResponseBodyMatchers.responseBody;
import static com.memes.test.utils.AuthorizationUtils.authHeaders;
import static com.memes.test.utils.RequestUtils.asJSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("IntegrationTest")
@SpringBootTest
@AutoConfigureMockMvc
public class CommentIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private CommentRepository commentRepository;

  @Autowired private UserRepository userRepository;

  @Autowired private CommentMapper commentMapper;

  @Test
  void getCommentById_CommentWasFound_Returns200AndCommentResponse() throws Exception {
    Comment comment = CommentTestBuilder.comment().build();
    comment = commentRepository.save(comment);

    mockMvc
        .perform(get("/comments/" + comment.getId()).contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(
            responseBody()
                .containsObjectAsJson(
                    commentMapper.commentToCommentResponseDto(comment), CommentResponseDto.class));
  }

  @Test
  void saveComment_IsSuccessful_ReturnsCommentResponse() throws Exception {
    User author = userRepository.save(UserTestBuilder.user().build());
    SaveCommentDto comment = CommentTestBuilder.saveCommentDto().build();

    mockMvc
        .perform(
            post("/comments/")
                .headers(authHeaders(author.getUsername()))
                .content(asJSON(comment))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.author.email").value(author.getEmail()))
        .andExpect(jsonPath("$.content").value(comment.getContent()));
  }
}
