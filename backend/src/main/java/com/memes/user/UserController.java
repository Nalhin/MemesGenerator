package com.memes.user;

import com.memes.auth.AuthUser;
import com.memes.shared.annotations.Authenticated;
import com.memes.shared.utils.CustomModelMapper;
import com.memes.user.dto.UserResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final CustomModelMapper modelMapper;

  @GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Get users")
  public ResponseEntity<List<UserResponseDto>> getAll() {
    return ResponseEntity.ok(
        modelMapper.mapList(this.userService.findAll(), UserResponseDto.class));
  }

  @GetMapping(path = "/users/me", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiResponses(@ApiResponse(responseCode = "200", description = "Get currently logged user"))
  @Authenticated
  public ResponseEntity<UserResponseDto> me(@AuthenticationPrincipal AuthUser authUser) {
    return ResponseEntity.ok(modelMapper.map(authUser.getUser(), UserResponseDto.class));
  }
}
