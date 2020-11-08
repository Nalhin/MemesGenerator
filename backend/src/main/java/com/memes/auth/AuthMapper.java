package com.memes.auth;

import com.memes.auth.dto.AuthResponseDto;
import com.memes.auth.dto.SignUpUserDto;
import com.memes.jwt.JwtMapper;
import com.memes.jwt.model.JwtPayload;
import com.memes.user.User;
import com.memes.user.UserMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.util.Pair;

@Mapper(
    componentModel = "spring",
    uses = {UserMapper.class, JwtMapper.class},
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AuthMapper {

  @Mapping(source = "first", target = "user")
  @Mapping(source = "second", target = "payload")
  AuthResponseDto authPairToResponse(Pair<User, JwtPayload> authPair);

  @Mapping(target = "id", ignore = true)
  User signUpUserDtoToUser(SignUpUserDto signUpUserDto);
}
