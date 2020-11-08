package com.memes.template;

import com.memes.template.test.TemplateTestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TemplateServiceTest {

  @Mock private TemplateRepository templateRepository;
  @InjectMocks private TemplateService templateService;

  @Nested
  class GetOneById {

    @Test
    @DisplayName("Should return template when found")
    void returnsTemplate() {
      Template expectedTemplate = TemplateTestFactory.template().build();
      when(templateRepository.findById(anyLong())).thenReturn(Optional.of(expectedTemplate));

      Template actualTemplate = templateService.getOneById(expectedTemplate.getId());

      assertThat(expectedTemplate).isEqualTo(actualTemplate);
    }

    @Test
    @DisplayName("Should throw ResponseStatusException when template is not found")
    void throwsResponseStatusException() {
      when(templateRepository.findById(anyLong())).thenReturn(Optional.empty());

      assertThatThrownBy(() -> templateService.getOneById(1L))
          .isInstanceOf(ResponseStatusException.class);
    }
  }

  @Nested
  class FindAll {

    @Test
    @DisplayName("Should query and return paginated templates")
    void returnsTemplates() {
      Page<Template> expectedPage = new PageImpl<>(TemplateTestFactory.templates(4));
      when(templateRepository.findAll(any(PageRequest.class))).thenReturn(expectedPage);

      Page<Template> actualPage = templateService.findAll(1);

      assertThat(actualPage).containsExactlyElementsOf(expectedPage);
    }
  }

  @Nested
  class Save {

    @Test
    @DisplayName("Should save and return saved template")
    void savesTemplate() {
      Template expectedTemplate = TemplateTestFactory.template().build();
      when(templateRepository.save(expectedTemplate)).then(returnsFirstArg());

      Template actualTemplate = templateService.save(expectedTemplate);

      assertThat(actualTemplate).isEqualTo(expectedTemplate);
    }
  }
}
