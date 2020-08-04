package com.memes.template;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TemplateServiceTest {

  @Mock private TemplateRepository templateRepository;
  @InjectMocks private TemplateService templateService;

  private final EasyRandom random = new EasyRandom();

  @Test
  void getOneById_TemplateFound_ReturnTemplate() {
    Template template = random.nextObject(Template.class);
    when(templateRepository.findById(anyLong())).thenReturn(Optional.of(template));

    Template result = templateService.getOneById(template.getId());

    assertEquals(template, result);
  }

  @Test
  void getOneById_TemplateNotFound_ThrowsResponseStatusException() {
    when(templateRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResponseStatusException.class, () -> templateService.getOneById(1L));
  }

  @Test
  void findAll_TemplatesPresent_ReturnsSameSize() {
    Page<Template> templatePage =
        new PageImpl<>(random.objects(Template.class, 4).collect(Collectors.toList()));
    when(templateRepository.findAll(any(PageRequest.class))).thenReturn(templatePage);

    Page<Template> result = templateService.findAll(1);

    assertEquals(templatePage.getSize(), result.getSize());
  }

  @Test
  void save_OperationSuccessful_ReturnsSavedTemplate() {
    Template template = random.nextObject(Template.class);
    when(templateRepository.save(template)).then(returnsFirstArg());

    Template result = templateService.save(template);

    assertEquals(template, result);
  }
}
