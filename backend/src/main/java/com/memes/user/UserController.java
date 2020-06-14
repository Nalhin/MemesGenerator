package com.memes.user;

import com.memes.auth.AuthUser;
import com.memes.user.dto.UserResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/users")
@Api(tags = "users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final ModelMapper modelMapper;

  @GetMapping
  @ApiOperation(value = "Get users")
  public @ResponseBody List<UserResponseDto> getAll() {
    return this.userService.findAll().stream()
        .map(u -> modelMapper.map(u, UserResponseDto.class))
        .collect(Collectors.toList());
  }

  @GetMapping(path = "/me")
  @ApiOperation(
      value = "Get current user",
      authorizations = {@Authorization(value = "Bearer %token")})
  public @ResponseBody UserResponseDto me(@AuthenticationPrincipal AuthUser authUser) {
    User user = userService.findOneByUsername(authUser.getUsername()).orElse(new User());
    return modelMapper.map(user, UserResponseDto.class);
  }
}
