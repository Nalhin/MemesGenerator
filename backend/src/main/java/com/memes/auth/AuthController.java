package com.memes.auth;

import com.memes.auth.dto.AuthResponseDto;
import com.memes.auth.dto.LoginUserDto;
import com.memes.auth.dto.RegisterUserDto;
import com.memes.user.User;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auth")
@Api(tags = "auth")
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
