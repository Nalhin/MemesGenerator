package com.memes.user;

import com.memes.test.auth.WithMockAnonymousUser;
import com.memes.test.auth.WithMockAuthenticatedUser;
import com.memes.test.utils.MockSecurityConfig;
import com.memes.user.dto.UserResponseDto;
import com.memes.user.test.UserTestFactory;
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

  @Nested
  class GetAll {

    @Test
    @DisplayName("Should return OK (200) status code and UserResponseDto List")
    void usersPresent() throws Exception {
      List<User> mockUsers = UserTestFactory.users(4);
      List<UserResponseDto> expectedResult = userMapper.usersToUserResponseDtoList(mockUsers);
      when(userService.findAll()).thenReturn(mockUsers);

      mockMvc
          .perform(get("/users").contentType(MediaType.APPLICATION_JSON_VALUE))
          .andExpect(status().isOk())
          .andExpect(responseBody().containsObjectAsJson(expectedResult, UserResponseDto[].class));

      verify(userService, times(1)).findAll();
    }
  }

  @Nested
  class Me {
    @Test
    @DisplayName(
        "Should return OK (200) status code and UserResponseDto when user is authenticated")
    @WithMockAuthenticatedUser
    void authenticatedUser() throws Exception {
      mockMvc
          .perform(get("/users/me").contentType(MediaType.APPLICATION_JSON_VALUE))
          .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return FORBIDDEN (403) status code when user is unauthenticated")
    @WithMockAnonymousUser
    void anonymousUser() throws Exception {
      mockMvc
          .perform(get("/users/me").contentType(MediaType.APPLICATION_JSON_VALUE))
          .andExpect(status().isForbidden());
    }
  }
}
