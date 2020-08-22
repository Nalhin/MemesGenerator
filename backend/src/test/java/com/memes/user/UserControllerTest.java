package com.memes.user;

import com.memes.testutils.auth.WithMockAnonymousUser;
import com.memes.testutils.auth.WithMockAuthenticatedUser;
import com.memes.user.dto.UserResponseDto;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;

import static com.memes.testutils.matchers.ResponseBodyMatchers.responseBody;
import static com.memes.testutils.matchers.RequestMatchers.assertThatRequest;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {
  @Autowired private MockMvc mockMvc;

  @Autowired private UserMapper userMapper;

  @MockBean private UserService userService;

  @Test
  void getAll_UsersPresent_Returns200AndUsers() throws Exception {
    List<User> mockUsers = new EasyRandom().objects(User.class, 2).collect(Collectors.toList());
    List<UserResponseDto> expected = userMapper.usersToUserResponseDtoList(mockUsers);
    when(userService.findAll()).thenReturn(mockUsers);

    mockMvc
        .perform(get("/users").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk())
        .andExpect(responseBody().containsObjectAsJson(expected, UserResponseDto[].class));

    verify(userService, times(1)).findAll();
  }

  @Test
  @WithMockAuthenticatedUser()
  void me_AuthenticatedUser_Returns200AndUserResponse() throws Exception {
    mockMvc
        .perform(get("/users/me").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockAnonymousUser()
  void me_whenUnauthenticatedUser_ThrowsUnauthorizedException() {
    assertThatRequest(
            () -> mockMvc.perform(get("/users/me")))
        .throwsUnauthorizedException();
  }
}
