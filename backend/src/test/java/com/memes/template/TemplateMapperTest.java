package com.memes.template;

import com.memes.template.dto.SaveTemplateDto;
import com.memes.template.dto.TemplateResponseDto;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemplateMapperTest {

  private final TemplateMapper templateMapper = new TemplateMapperImpl();

  @Test
  void saveTemplateDtoToTemplate_FilledSaveTemplateDto_ReturnsTemplate() {
    SaveTemplateDto saveTemplateDto = new EasyRandom().nextObject(SaveTemplateDto.class);

    Template result = templateMapper.saveTemplateDtoToTemplate(saveTemplateDto);

    assertAll(
        () -> assertEquals(saveTemplateDto.getTitle(), result.getTitle()),
        () -> assertEquals(saveTemplateDto.getUrl(), result.getUrl()));
  }

  @Test
  void templateToTemplateResponseDto_FilledTemplate_ReturnsTemplateResponseDto() {
    Template template = new EasyRandom().nextObject(Template.class);

    TemplateResponseDto result = templateMapper.templateToTemplateResponseDto(template);

    assertAll(
        () -> assertEquals(template.getTitle(), result.getTitle()),
        () -> assertEquals(template.getUrl(), result.getUrl()),
        () -> assertEquals(template.getId(), result.getId()));
  }
}
