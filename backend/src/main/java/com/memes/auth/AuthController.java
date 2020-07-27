package com.memes.auth;

import com.memes.auth.dto.AuthResponseDto;
import com.memes.auth.dto.LoginUserDto;
import com.memes.auth.dto.SignUpUserDto;
import com.memes.shared.utils.CustomModelMapper;
import com.memes.user.User;
import com.memes.user.dto.UserResponseDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final CustomModelMapper modelMapper;

  @PostMapping(path = "/auth/login", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AuthResponseDto> login(@RequestBody LoginUserDto loginUserDto) {
    AuthResponseDto authResponseDto = new AuthResponseDto();

    Pair<User, String> result =
        authService.login(loginUserDto.getUsername(), loginUserDto.getUsername());
    authResponseDto.setToken(result.getSecond());
    authResponseDto.setUser(modelMapper.map(result.getFirst(), UserResponseDto.class));

    return ResponseEntity.ok(authResponseDto);
  }

  @PostMapping(path = "/auth/sign-up", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AuthResponseDto> signUp(@RequestBody SignUpUserDto signUpUserDto) {
    AuthResponseDto authResponseDto = new AuthResponseDto();

    Pair<User, String> result =
        authService.signUp(modelMapper.map(signUpUserDto, User.class));
    authResponseDto.setToken(result.getSecond());
    authResponseDto.setUser(modelMapper.map(result.getFirst(), UserResponseDto.class));

    return ResponseEntity.ok(authResponseDto);
  }
}
