package com.memes.user;

import com.memes.user.test.UserTestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock private UserRepository userRepository;

  @InjectMocks private UserService userService;

  @Nested
  class FindAll {

    @Test
    @DisplayName("Should return users if found")
    void returnsUsers() {
      List<User> mockUsers = UserTestBuilder.users(4);
      when(userRepository.findAll()).thenReturn(mockUsers);

      List<User> result = userService.findAll();

      assertThat(mockUsers).isEqualTo(result);
    }
  }

  @Nested
  class FindOneByUsername {

    @Test
    @DisplayName("Should return user if user is found")
    void userFound() {
      User expectedResult = UserTestBuilder.user().build();
      when(userRepository.findOneByUsername(expectedResult.getUsername()))
          .thenReturn(Optional.of(expectedResult));

      Optional<User> actualResult = userService.findOneByUsername(expectedResult.getUsername());

      assertThat(actualResult).contains(expectedResult);
    }

    @Test
    @DisplayName("Should return empty optional if user is not found")
    void userNotFound() {
      User expectedResult = UserTestBuilder.user().build();
      when(userRepository.findOneByUsername(expectedResult.getUsername()))
          .thenReturn(Optional.empty());

      Optional<User> actualResult = userService.findOneByUsername(expectedResult.getUsername());

      assertThat(actualResult).isEmpty();
    }
  }

  @Nested
  class Save {

    @Test
    @DisplayName("Should persist provided data")
    void savesUserData() {
      User user = UserTestBuilder.user().build();

      userService.save(user);

      verify(userRepository).save(user);
    }
  }
}
