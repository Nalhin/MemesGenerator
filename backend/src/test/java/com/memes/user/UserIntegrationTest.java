package com.memes.user;

import com.memes.user.dto.UserResponseDto;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.memes.test.matchers.ResponseBodyMatchers.responseBody;
import static com.memes.test.utils.AuthorizationUtils.authHeaders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("IntegrationTest")
@SpringBootTest
@AutoConfigureMockMvc
public class UserIntegrationTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private UserRepository userRepository;

  @Autowired private UserMapper userMapper;

  @Test
  void getAll_UsersPresent_Returns200AndUsers() throws Exception {
    List<User> users = UserTestBuilder.users(4);
    userRepository.saveAll(users);
    List<UserResponseDto> expected = userMapper.usersToUserResponseDtoList(users);

    mockMvc
        .perform(get("/users").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(responseBody().containsObjectAsJson(expected, UserResponseDto[].class));
  }

  @Test
  void me_AuthenticatedUser_Returns200AndUserResponse() throws Exception {
    User user = UserTestBuilder.user().build();
    userRepository.save(user);
    UserResponseDto expected = userMapper.userToUserResponseDto(user);

    mockMvc
        .perform(
            get("/users/me")
                .headers(authHeaders(user.getUsername()))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(responseBody().containsObjectAsJson(expected, UserResponseDto.class));
  }

  @Test
  void me_UnauthenticatedUser_Returns403() throws Exception {
    mockMvc
        .perform(get("/users/me").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isForbidden());
  }
}
