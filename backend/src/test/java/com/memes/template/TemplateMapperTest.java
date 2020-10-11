package com.memes.template;

import com.memes.template.dto.SaveTemplateDto;
import com.memes.template.dto.TemplateResponseDto;
import com.memes.template.test.TemplateTestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TemplateMapperTest {

  private final TemplateMapper templateMapper = new TemplateMapperImpl();

  @Nested
  class SaveTemplateDtoToTemplate {

    @Test
    @DisplayName("Should map SaveTemplateDto to Template")
    void mapsFromDto() {
      SaveTemplateDto providedDto = TemplateTestBuilder.saveTemplateDto().build();

      Template result = templateMapper.saveTemplateDtoToTemplate(providedDto);

      assertThat(result)
          .extracting("title", "url")
          .containsExactly(providedDto.getTitle(), providedDto.getUrl());
    }
  }

  @Nested
  class TemplateToTemplateResponseDto {

    @Test
    @DisplayName("Should map Template to TemplateResponseDto")
    void mapsToDto() {
      Template template = TemplateTestBuilder.template().build();

      TemplateResponseDto result = templateMapper.templateToTemplateResponseDto(template);

      assertThat(result)
          .extracting("id", "title", "url")
          .containsExactly(template.getId(), template.getTitle(), template.getUrl());
    }
  }
}
