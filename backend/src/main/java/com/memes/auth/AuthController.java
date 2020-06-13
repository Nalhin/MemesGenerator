package com.memes.auth;

import com.memes.auth.dto.AuthResponseDto;
import com.memes.auth.dto.LoginUserDto;
import com.memes.auth.dto.RegisterUserDto;
import com.memes.user.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller()
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final ModelMapper modelMapper;

  @PostMapping("/login")
  public @ResponseBody AuthResponseDto login(@RequestBody LoginUserDto loginUserDto) {
    AuthResponseDto authResponseDto = new AuthResponseDto();
    authResponseDto.setToken(
        authService.login(loginUserDto.getUsername(), loginUserDto.getUsername()));
    return authResponseDto;
  }

  @PostMapping("/register")
  public @ResponseBody AuthResponseDto register(@RequestBody RegisterUserDto registerUserDto) {
    AuthResponseDto authResponseDto = new AuthResponseDto();
    authResponseDto.setToken(authService.register(modelMapper.map(registerUserDto, User.class)));
    return authResponseDto;
  }
}
