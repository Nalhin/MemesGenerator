package com.memes.user;

import com.memes.test.auth.WithMockAnonymousUser;
import com.memes.test.auth.WithMockAuthenticatedUser;
import com.memes.test.utils.MockSecurityConfig;
import com.memes.user.dto.UserResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.memes.test.matchers.ResponseBodyMatchers.responseBody;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = {UserController.class})
@Import({UserMapperImpl.class})
class UserControllerTest extends MockSecurityConfig {

  @Autowired private MockMvc mockMvc;

  @Autowired private UserMapper userMapper;

  @MockBean private UserService userService;

  @Test
  void getAll_UsersPresent_Returns200AndUsers() throws Exception {
    List<User> mockUsers = UserTestBuilder.users(4);
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
  void me_UnauthenticatedUser_Returns403() throws Exception {
    mockMvc
        .perform(get("/users/me").contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(status().isForbidden());
  }
}
