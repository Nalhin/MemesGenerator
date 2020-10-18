package com.memes.auth;

import com.memes.auth.dto.LoginUserDto;
import com.memes.auth.dto.SignUpUserDto;
import com.memes.auth.test.AuthTestBuilder;
import com.memes.test.annotations.IntegrationTest;
import com.memes.user.UserRepository;
import com.memes.user.test.UserTestBuilder;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.memes.test.matchers.ResponseBodyMatchers.responseBody;
import static com.memes.test.utils.RequestUtils.asJSON;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthIntegrationTest {

  @LocalServerPort private int port;

  @Autowired private UserRepository userRepository;
  @Autowired private PasswordEncoder passwordEncoder;

  @BeforeEach
  void setup() {
    RestAssured.port = port;
  }

  @AfterEach
  void tearDown() {
    userRepository.deleteAll();
  }

  @Nested
  class Login {

    @Test
    @DisplayName("Should return OK (200) status code and AuthResponseDto")
    void returns200() {
      LoginUserDto loginUserDto = AuthTestBuilder.loginUserDto().build();
      userRepository.save(
          UserTestBuilder.user()
              .username(loginUserDto.getUsername())
              .password(passwordEncoder.encode(loginUserDto.getPassword()))
              .build());

      given()
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .accept(MediaType.APPLICATION_JSON_VALUE)
          .body(loginUserDto)
          .when()
          .post("/auth/login")
          .then()
          .assertThat()
          .statusCode(HttpStatus.OK.value())
          .and()
          .body("token", notNullValue());
    }

    @Test
    @DisplayName("Should return FORBIDDEN (403) status code when invalid credentials are provided")
    void returns403() {
      LoginUserDto loginUserDto = AuthTestBuilder.loginUserDto().build();
      userRepository.save(UserTestBuilder.user().username(loginUserDto.getUsername()).build());

      given()
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .accept(MediaType.APPLICATION_JSON_VALUE)
          .body(loginUserDto)
          .when()
          .post("/auth/login")
          .then()
          .assertThat()
          .statusCode(HttpStatus.FORBIDDEN.value());
    }
  }

  @Nested
  class SignUp {

    @Test
    @DisplayName("Should return OK (200) status code and AuthResponseDto")
    void returns200() {
      SignUpUserDto requestBody = AuthTestBuilder.signUpUserDto().build();

      given()
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .accept(MediaType.APPLICATION_JSON_VALUE)
          .body(requestBody)
          .when()
          .post("/auth/sign-up")
          .then()
          .assertThat()
          .statusCode(HttpStatus.OK.value())
          .and()
          .body("user.username", equalTo(requestBody.getUsername()), "token", notNullValue());
    }

    @Test
    @DisplayName("Should return CONFLICT (409) status code when email is taken")
    void returns409() {
      SignUpUserDto requestBody = AuthTestBuilder.signUpUserDto().build();
      userRepository.save(UserTestBuilder.user().email(requestBody.getEmail()).build());

      given()
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .accept(MediaType.APPLICATION_JSON_VALUE)
          .body(requestBody)
          .when()
          .post("/auth/sign-up")
          .then()
          .assertThat()
          .statusCode(HttpStatus.CONFLICT.value());
    }
  }
}
