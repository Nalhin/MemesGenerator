package com.memes.auth;

import com.memes.auth.dto.AuthResponseDto;
import com.memes.auth.dto.LoginUserDto;
import com.memes.auth.dto.SignUpUserDto;
import com.memes.user.User;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(tags = "auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final AuthMapper authMapper;

  @PostMapping(path = "/auth/login", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginUserDto loginUserDto) {

    Pair<User, String> result =
        authService.login(loginUserDto.getUsername(), loginUserDto.getPassword());

    return ResponseEntity.ok(authMapper.authPairToUserResponseDto(result));
  }

  @PostMapping(path = "/auth/sign-up", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AuthResponseDto> signUp(@Valid @RequestBody SignUpUserDto signUpUserDto) {

    Pair<User, String> result = authService.signUp(authMapper.signUpUserDtoToUser(signUpUserDto));

    return ResponseEntity.ok(authMapper.authPairToUserResponseDto(result));
  }
}
