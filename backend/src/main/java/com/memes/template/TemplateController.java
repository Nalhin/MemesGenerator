package com.memes.template;

import com.memes.template.dto.SaveTemplateDto;
import com.memes.template.dto.TemplateResponseDto;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/templates")
@Api(tags = "templates")
@AllArgsConstructor
public class TemplateController {

  private final TemplateService templateService;
  private final ModelMapper modelMapper;

  @GetMapping(path = "/{templateId}")
  public @ResponseBody TemplateResponseDto getById(@PathVariable Long templateId) {
    return modelMapper.map(templateService.getOneById(templateId), TemplateResponseDto.class);
  }

  @GetMapping(path = "")
  public @ResponseBody Page<TemplateResponseDto> getAll(
      @RequestParam(name = "page", defaultValue = "0") int page) {
    return templateService
        .findAll(page)
        .map(template -> modelMapper.map(template, TemplateResponseDto.class));
  }

  @PostMapping("/")
  public @ResponseBody TemplateResponseDto addTemplate(
      @RequestBody SaveTemplateDto saveTemplateDto) {
    return modelMapper.map(
        templateService.save(modelMapper.map(saveTemplateDto, Template.class)),
        TemplateResponseDto.class);
  }
}
