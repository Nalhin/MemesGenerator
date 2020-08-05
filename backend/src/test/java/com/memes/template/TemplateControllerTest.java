package com.memes.template;

import com.memes.template.dto.SaveTemplateDto;
import com.memes.template.dto.TemplateResponseDto;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
    templateController = new TemplateController(templateService, new TemplateMapperImpl());
  }

  @Test
  void getById_IsPresent_ReturnsResponseWithTemplate() {
    Template template = random.nextObject(Template.class);
    when(templateService.getOneById(template.getId())).thenReturn(template);

    ResponseEntity<TemplateResponseDto> result = templateController.getById(template.getId());

    assertNotNull(result.getBody());
    assertEquals(template.getId(), result.getBody().getId());
  }

  @Test
  void getAll_TemplatesPresent_ReturnsResponseWithSameSize() {
    Page<Template> returnedTemplates =
        new PageImpl<>(random.objects(Template.class, 4).collect(Collectors.toList()));
    when(templateService.findAll(anyInt())).thenReturn(returnedTemplates);

    ResponseEntity<Page<TemplateResponseDto>> result = templateController.getAll(1);

    assertNotNull(result.getBody());
    assertEquals(4, result.getBody().getSize());
  }

  @Test
  void saveTemplate_OperationSuccessful_ReturnsSavedTemplate() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    Template savedTemplate = random.nextObject(Template.class);
    when(templateService.save(any(Template.class))).thenReturn(savedTemplate);

    ResponseEntity<TemplateResponseDto> result =
        templateController.saveTemplate(random.nextObject(SaveTemplateDto.class));

    assertNotNull(result.getBody());
    assertEquals(savedTemplate.getId(), result.getBody().getId());
  }
}
