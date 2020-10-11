package com.memes.meme;

import com.memes.auth.models.AnonymousUser;
import com.memes.auth.models.AuthenticatedUser;
import com.memes.auth.test.AuthTestBuilder;
import com.memes.meme.dto.SaveMemeDto;
import com.memes.meme.test.MemeTestBuilder;
import com.memes.template.Template;
import com.memes.template.TemplateRepository;
import com.memes.template.test.TemplateTestBuilder;
import com.memes.upload.FileUploadService;
import com.memes.upload.exceptions.ImageNotSavedException;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemeServiceTest {

  @Mock private MemeRepository memeRepository;
  @Mock private FileUploadService fileUploadService;
  @Mock private TemplateRepository templateRepository;

  @InjectMocks private MemeService memeService;

  private final MultipartFile file = new MockMultipartFile("d", "d".getBytes());

  @Nested
  class getOneById {

    @Test
    @DisplayName("Should return template when found")
    void returnsTemplate() {
      Meme expectedMeme = MemeTestBuilder.meme().build();
      when(memeRepository.findById(anyLong())).thenReturn(Optional.of(expectedMeme));

      Meme actualMeme = memeService.getOneById(expectedMeme.getId());

      assertThat(expectedMeme).isEqualTo(actualMeme);
    }

    @Test
    @DisplayName("Should throw ResponseStatusException when meme is not found")
    void throwsResponseStatusException() {
      when(memeRepository.findById(anyLong())).thenReturn(Optional.empty());

      assertThatThrownBy(() -> memeService.getOneById(1L))
          .isInstanceOf(ResponseStatusException.class);
    }
  }

  @Nested
  class FindAll {

    @Test
    @DisplayName("Should query and return paginated Memes")
    void returnsMemes() {
      Page<Meme> expectedPage = new PageImpl<>(MemeTestBuilder.memes(4));
      when(memeRepository.findAll(any(PageRequest.class))).thenReturn(expectedPage);

      Page<Meme> actualPage = memeService.findAll(1);

      assertThat(actualPage).containsExactlyElementsOf(expectedPage);
    }
  }

  @Nested
  class Save {

    @Test
    @DisplayName("Should set template, user and persist meme")
    void persistsWithAuthenticated() {
      SaveMemeDto saveMemeDto = SaveMemeDto.builder().build();
      AuthenticatedUser authUser = AuthTestBuilder.authUser().build();
      Template template = TemplateTestBuilder.template().build();
      when(templateRepository.getOne(saveMemeDto.getTemplateId())).thenReturn(template);
      when(memeRepository.save(any(Meme.class))).then(returnsFirstArg());

      Meme actualMeme = memeService.save(saveMemeDto, file, authUser);

      assertThat(actualMeme)
          .extracting("author", "template")
          .containsExactly(authUser.getPresentUser(), template);
    }

    @Test
    @DisplayName("Should set meme template, leave user empty if user is anonymous and persist meme")
    void setsTemplate() {
      Template template = TemplateTestBuilder.template().build();
      SaveMemeDto saveMemeDto = MemeTestBuilder.saveMemeDto().build();
      when(templateRepository.getOne(saveMemeDto.getTemplateId())).thenReturn(template);
      when(memeRepository.save(any(Meme.class))).then(returnsFirstArg());

      Meme actualMeme = memeService.save(saveMemeDto, file, new AnonymousUser());

      assertThat(actualMeme).extracting("template", "author").containsExactly(template, null);
    }

    @Test
    @DisplayName("Should throw ResponseStatusException if file upload is unsuccessful")
    void throwsIfUploadFailed() throws ImageNotSavedException {
      when(fileUploadService.uploadFile(any(MultipartFile.class), anyString()))
          .thenThrow(ImageNotSavedException.class);

      assertThatThrownBy(
              () ->
                  memeService.save(
                      MemeTestBuilder.saveMemeDto().build(), file, new AnonymousUser()))
          .isInstanceOf(ResponseStatusException.class);
    }
  }
}
