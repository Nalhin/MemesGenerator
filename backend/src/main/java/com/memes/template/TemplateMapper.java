package com.memes.template;

import com.memes.template.dto.SaveTemplateDto;
import com.memes.template.dto.TemplateResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TemplateMapper {

  @Mapping(target = "id", ignore = true)
  Template saveTemplateDtoToTemplate(SaveTemplateDto saveTemplateDto);

  TemplateResponseDto templateToTemplateResponseDto(Template template);
}
