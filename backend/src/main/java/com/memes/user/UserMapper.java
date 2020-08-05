package com.memes.user;

import com.memes.user.dto.UserResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserResponseDto userToUserResponseDto(User user);

  List<UserResponseDto> usersToUserResponseDtoList(List<User> users);
}
