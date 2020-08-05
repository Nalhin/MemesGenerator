package com.memes.meme;

import com.memes.meme.dto.MemeResponseDto;
import com.memes.user.UserMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Value;

@Mapper(
    componentModel = "spring",
    uses = UserMapper.class,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class MemeMapper {

  @Value("${images.url-prefix}")
  private String imageUrlPrefix;

  @Mapping(target = "url", expression = "java(getImageUrlPrefix() + meme.getFilename())")
  public abstract MemeResponseDto memeToMemeResponseDto(Meme meme);

  protected String getImageUrlPrefix() {
    return imageUrlPrefix;
  }
}
