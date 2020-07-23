package com.memes.auth;

import com.memes.auth.dto.AuthResponseDto;
import com.memes.auth.dto.LoginUserDto;
import com.memes.auth.dto.SignUpUserDto;
import com.memes.user.User;
import com.memes.user.dto.UserResponseDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.Pair;
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
    Pair<User, String> result =
        authService.login(loginUserDto.getUsername(), loginUserDto.getUsername());
    authResponseDto.setToken(result.getSecond());
    authResponseDto.setUser(modelMapper.map(result.getFirst(), UserResponseDto.class));
    return authResponseDto;
  }

  @PostMapping("/sign-up")
  public @ResponseBody AuthResponseDto signUp(@RequestBody SignUpUserDto signUpUserDto) {
    AuthResponseDto authResponseDto = new AuthResponseDto();
    Pair<User, String> result = authService.signUp(modelMapper.map(signUpUserDto, User.class));
    authResponseDto.setToken(result.getSecond());
    authResponseDto.setUser(modelMapper.map(result.getFirst(), UserResponseDto.class));
    return authResponseDto;
  }
}
