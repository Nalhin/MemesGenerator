package com.memes.user;

import com.memes.test.annotations.IntegrationTest;
import com.memes.user.test.UserTestBuilder;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static com.memes.test.utils.AuthorizationUtils.restAuthHeaders;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@IntegrationTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest {

  @LocalServerPort private int port;

  @Autowired private UserRepository userRepository;

  @BeforeEach
  void setup() {
    RestAssured.port = port;
  }

  @AfterEach
  void tearDown() {
    userRepository.deleteAll();
  }

  @Nested
  class GetAll {
    @Test
    @DisplayName("Should return OK (200) status code and UserResponseDto List")
    void returns200() {
      List<User> expectedUsers = userRepository.saveAll(UserTestBuilder.users(4));

      given()
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .accept(MediaType.APPLICATION_JSON_VALUE)
          .when()
          .get("/users")
          .then()
          .assertThat()
          .statusCode(HttpStatus.OK.value())
          .and()
          .body(
              "content.size()",
              equalTo(4),
              "[0].id",
              equalTo(expectedUsers.get(0).getId().intValue()),
              "[1].username",
              equalTo(expectedUsers.get(1).getUsername()),
              "[2].email",
              equalTo(expectedUsers.get(2).getEmail()));
    }
  }

  @Nested
  class Me {
    @Test
    @DisplayName(
        "Should return OK (200) status code and UserResponseDto when user is authenticated")
    void returns200() {
      User providedUser = userRepository.save(UserTestBuilder.user().build());

      given()
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .accept(MediaType.APPLICATION_JSON_VALUE)
          .header(restAuthHeaders(providedUser.getUsername()))
          .when()
          .get("/users/me")
          .then()
          .assertThat()
          .statusCode(HttpStatus.OK.value())
          .and()
          .body(
              "email",
              equalTo(providedUser.getEmail()),
              "username",
              equalTo(providedUser.getUsername()),
              "id",
              equalTo(providedUser.getId().intValue()));
    }

    @Test
    @DisplayName("Should return UNAUTHORIZED (403) status code when user is not authenticated")
    void returns403() {
      given()
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .accept(MediaType.APPLICATION_JSON_VALUE)
          .when()
          .get("/users/me")
          .then()
          .assertThat()
          .statusCode(HttpStatus.FORBIDDEN.value());
    }
  }
}
