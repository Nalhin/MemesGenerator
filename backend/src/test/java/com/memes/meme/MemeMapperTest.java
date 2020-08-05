package com.memes.meme;

import com.memes.meme.dto.MemeResponseDto;
import com.memes.user.UserMapperImpl;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MemeMapperImpl.class, UserMapperImpl.class})
@TestPropertySource(properties = "images.url-prefix=prefix")
class MemeMapperTest {

  private final String PREFIX = "prefix";
  @Autowired MemeMapper memeMapper;

  @Test
  void memeToMemeResponseDto_FilledMeme_ReturnsMemeResponseDto() {
    Meme meme = new EasyRandom().nextObject(Meme.class);

    MemeResponseDto result = memeMapper.memeToMemeResponseDto(meme);

    assertAll(
        () -> assertEquals(meme.getCreated(), result.getCreated()),
        () -> assertEquals(meme.getAuthor().getEmail(), result.getAuthor().getEmail()),
        () -> assertEquals(meme.getTemplate().getId(), result.getTemplate().getId()),
        () -> assertEquals(meme.getId(), result.getId()));
  }

  @Test
  void memeToMemeResponseDto_MemeWithFilename_PrefixesFilename() {
    Meme meme = new EasyRandom().nextObject(Meme.class);

    MemeResponseDto result = memeMapper.memeToMemeResponseDto(meme);

    assertEquals(PREFIX + meme.getFilename(), result.getUrl());
  }
}
