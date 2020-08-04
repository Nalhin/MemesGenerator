package com.memes.template;

import com.memes.shared.utils.CustomModelMapper;
import com.memes.template.dto.SaveTemplateDto;
import com.memes.template.dto.TemplateResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@Api(tags = "templates")
@RequiredArgsConstructor
public class TemplateController {

  private final TemplateService templateService;
  private final CustomModelMapper modelMapper;

  @GetMapping(path = "/templates/{templateId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Get template by id")
  public ResponseEntity<TemplateResponseDto> getById(@PathVariable Long templateId) {
    TemplateResponseDto templateResponseDto =
        modelMapper.map(templateService.getOneById(templateId), TemplateResponseDto.class);

    return ResponseEntity.ok(templateResponseDto);
  }

  @GetMapping(path = "/templates", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Get templates page")
  public ResponseEntity<Page<TemplateResponseDto>> getAll(
      @RequestParam(name = "page", defaultValue = "0") int page) {
    Page<TemplateResponseDto> templateResponseDtoPage =
        templateService
            .findAll(page)
            .map(template -> modelMapper.map(template, TemplateResponseDto.class));

    return ResponseEntity.ok(templateResponseDtoPage);
  }

  @PostMapping(path = "/templates", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Save template")
  public ResponseEntity<TemplateResponseDto> saveTemplate(
      @RequestBody SaveTemplateDto saveTemplateDto) {
    Template savedTemplate =
        templateService.save(modelMapper.map(saveTemplateDto, Template.class));

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(savedTemplate.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(modelMapper.map(savedTemplate, TemplateResponseDto.class));
  }
}
