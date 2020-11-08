package com.memes.meme.test;

import com.github.javafaker.Faker;
import com.memes.meme.Meme;
import com.memes.meme.dto.MemeResponseDto;
import com.memes.meme.dto.SaveMemeDto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MemeTestFactory {
  private static final Faker faker = new Faker();

  public static Meme.MemeBuilder meme() {
    return Meme.builder()
        .created(LocalDate.now())
        .filename(faker.file().fileName())
        .id(faker.number().numberBetween(0, Long.MAX_VALUE));
  }

  public static List<Meme> memes(int count) {
    return Stream.generate(() -> meme().build()).limit(count).collect(Collectors.toList());
  }

  public static MemeResponseDto.MemeResponseDtoBuilder memeResponseDto() {
    return MemeResponseDto.builder()
        .created(LocalDate.now())
        .id(faker.number().numberBetween(0, Long.MAX_VALUE))
        .url(faker.internet().url());
  }

  public static SaveMemeDto.SaveMemeDtoBuilder saveMemeDto() {
    return SaveMemeDto.builder().templateId(faker.number().numberBetween(0, Long.MAX_VALUE));
  }
}
