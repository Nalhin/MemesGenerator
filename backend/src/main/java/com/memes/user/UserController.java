package com.memes.user;

import com.memes.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final ModelMapper modelMapper;

  @GetMapping(path = "/all")
  public @ResponseBody List<UserResponseDto> getAll() {
    return this.userService.findAll().stream()
        .map(u -> modelMapper.map(u, UserResponseDto.class))
        .collect(Collectors.toList());
  }

  @GetMapping(path = "/me")
  public @ResponseBody UserResponseDto me(Principal principal) {
    User user = userService.findOneByUsername(principal.getName()).orElse(new User());
    return modelMapper.map(user, UserResponseDto.class);
  }
}
