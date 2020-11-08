package com.memes.jwt;

import com.memes.jwt.dto.JwtPayloadResponseDto;
import com.memes.jwt.model.JwtPayload;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JwtMapper {

  JwtPayloadResponseDto jwtPayloadToResponse(JwtPayload user);
}
