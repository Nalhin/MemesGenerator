package com.memes.comment;

import com.memes.comment.dto.SaveCommentDto;
import com.memes.comment.test.CommentTestFactory;
import com.memes.test.annotations.IntegrationTest;
import com.memes.user.User;
import com.memes.user.UserRepository;
import com.memes.user.test.UserTestFactory;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.memes.test.utils.AuthorizationUtils.authHeaders;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@IntegrationTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentIntegrationTest {

  @Autowired private CommentRepository commentRepository;

  @Autowired private UserRepository userRepository;

  @LocalServerPort private int port;

  private RequestSpecification restClient;

  @BeforeEach
  void setup() {
    restClient =
        RestAssured.given()
            .port(port)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE);
  }

  @AfterEach
  void tearDown() {
    commentRepository.deleteAll();
    userRepository.deleteAll();
  }

  @Nested
  class GetCommentById {

    @Test
    @DisplayName("Should return OK (200) status code and CommentResponseDto")
    void returns200() {
      Comment savedComment =
          commentRepository.save(
              CommentTestFactory.comment()
                  .author(userRepository.save(UserTestFactory.user().build()))
                  .build());

      restClient
          .when()
          .get("/comments/" + savedComment.getId())
          .then()
          .assertThat()
          .statusCode(HttpStatus.OK.value())
          .and()
          .body(
              "content",
              equalTo(savedComment.getContent()),
              "id",
              equalTo(savedComment.getId().intValue()),
              "author.username",
              equalTo(savedComment.getAuthor().getUsername()));
    }
  }

  @Nested
  class SaveComment {

    @Test
    @DisplayName(
        "Should return CREATED (201) status code and CommentResponseDto when user is authenticated")
    void returns201() {
      User author = userRepository.save(UserTestFactory.user().build());
      SaveCommentDto comment = CommentTestFactory.saveCommentDto().build();

      restClient
          .header(authHeaders(author.getUsername()))
          .body(comment)
          .when()
          .post("/comments")
          .then()
          .assertThat()
          .statusCode(HttpStatus.CREATED.value())
          .and()
          .body("content", equalTo(comment.getContent()), "id", notNullValue());
    }
  }
}
