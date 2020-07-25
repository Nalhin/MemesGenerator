package com.memes.template;

import com.memes.shared.utils.CustomModelMapper;
import com.memes.template.dto.SaveTemplateDto;
import com.memes.template.dto.TemplateResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/api/templates")
@Api(tags = "templates")
@AllArgsConstructor
public class TemplateController {

  private final TemplateService templateService;
  private final CustomModelMapper customModelMapper;

  @GetMapping(path = "/{templateId}")
  @ApiOperation(value = "Get template by id")
  public ResponseEntity<TemplateResponseDto> getById(@PathVariable Long templateId) {
    TemplateResponseDto templateResponseDto =
        customModelMapper.map(templateService.getOneById(templateId), TemplateResponseDto.class);

    return ResponseEntity.ok(templateResponseDto);
  }

  @GetMapping(path = "")
  @ApiOperation(value = "Get templates page")
  public ResponseEntity<Page<TemplateResponseDto>> getAll(
      @RequestParam(name = "page", defaultValue = "0") int page) {
    Page<TemplateResponseDto> templateResponseDtoPage =
        templateService
            .findAll(page)
            .map(template -> customModelMapper.map(template, TemplateResponseDto.class));
    return ResponseEntity.ok(templateResponseDtoPage);
  }

  @PostMapping(path = "/save")
  @ApiOperation(value = "Save template")
  public ResponseEntity<TemplateResponseDto> addTemplate(
      @RequestBody SaveTemplateDto saveTemplateDto) {
    Template savedTemplate =
        templateService.save(customModelMapper.map(saveTemplateDto, Template.class));
    return ResponseEntity.created(URI.create("/" + savedTemplate.getId()))
        .body(customModelMapper.map(savedTemplate, TemplateResponseDto.class));
  }
}
