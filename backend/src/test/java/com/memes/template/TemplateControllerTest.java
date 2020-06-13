package com.memes.template;

import com.memes.template.dto.SaveTemplateDto;
import com.memes.template.dto.TemplateResponseDto;
import com.memes.user.User;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TemplateControllerTest {

  @Mock private TemplateService templateService;
  private TemplateController templateController;

  private final EasyRandom random = new EasyRandom();

  @BeforeEach
  void setUp() {
    templateController = new TemplateController(templateService, new ModelMapper());
  }

  @Test
  void getById() {
    Template template = random.nextObject(Template.class);
    when(templateService.getOneById(template.getId())).thenReturn(template);

    TemplateResponseDto response = templateController.getById(template.getId());

    assertEquals(template.getId(), response.getId());
  }

  @Test
  void getAll() {
    Page<Template> returnedTemplates =
        new PageImpl<>(random.objects(Template.class, 4).collect(Collectors.toList()));
    when(templateService.findAll(anyInt())).thenReturn(returnedTemplates);

    Page<TemplateResponseDto> result = templateController.getAll(1);

    assertEquals(4, result.getTotalElements());
  }

  @Test
  void addTemplate() {
    Template savedTemplate = random.nextObject(Template.class);
    when(templateService.save(any(Template.class))).thenReturn(savedTemplate);

    TemplateResponseDto response =
        templateController.addTemplate(random.nextObject(SaveTemplateDto.class));

    assertEquals(savedTemplate.getId(), response.getId());
  }
}
