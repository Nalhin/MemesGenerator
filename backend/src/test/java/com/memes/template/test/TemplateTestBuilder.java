package com.memes.template.test;

import com.github.javafaker.Faker;
import com.memes.template.Template;
import com.memes.template.dto.SaveTemplateDto;
import com.memes.template.dto.TemplateResponseDto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TemplateTestBuilder {

  private static final Faker faker = new Faker();

  public static Template.TemplateBuilder template() {
    return Template.builder()
        .title(faker.name().title())
        .url(faker.internet().url())
        .id(faker.number().numberBetween(0, Long.MAX_VALUE));
  }

  public static TemplateResponseDto.TemplateResponseDtoBuilder templateResponseDto() {
    Template template = template().build();
    return TemplateResponseDto.builder()
        .title(template.getTitle())
        .url(template.getUrl())
        .id(template.getId());
  }

  public static SaveTemplateDto.SaveTemplateDtoBuilder saveTemplateDto() {
    Template template = template().build();

    return SaveTemplateDto.builder().title(template.getTitle()).url(template.getUrl());
  }

  public static List<Template> templates(int count) {
    return Stream.generate(() -> template().build()).limit(count).collect(Collectors.toList());
  }
}
