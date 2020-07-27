package com.memes.meme;

import com.memes.auth.AuthUser;
import com.memes.meme.dto.SaveMemeDto;
import com.memes.template.Template;
import com.memes.upload.FileUploadService;
import com.memes.user.User;
import org.jeasy.random.EasyRandom;
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

import javax.persistence.EntityManager;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemeServiceTest {

  @Mock private MemeRepository memeRepository;
  @Mock private FileUploadService fileUploadService;
  @Mock private EntityManager entityManager;

  @InjectMocks private MemeService memeService;

  private final EasyRandom random = new EasyRandom();
  private final MultipartFile file = new MockMultipartFile("d", "d".getBytes());

  @Test
  void getOneById_IsPresent_ReturnsMeme() {
    Meme foundMeme = random.nextObject(Meme.class);
    when(memeRepository.findById(anyLong())).thenReturn(Optional.of(foundMeme));

    Meme result = memeService.getOneById(1L);

    assertEquals(foundMeme, result);
  }

  @Test
  void getOneById_NotFound_ThrowsResponseStatusException() {
    when(memeRepository.findById(anyLong())).thenReturn(Optional.empty());

    assertThrows(ResponseStatusException.class, () -> memeService.getOneById(1L));
  }

  @Test
  void findAll_MemesAvailable_ReturnsSameSize() {
    Page<Meme> memePage =
        new PageImpl<>(random.objects(Meme.class, 4).collect(Collectors.toList()));
    when(memeRepository.findAll(any(PageRequest.class))).thenReturn(memePage);

    Page<Meme> result = memeService.findAll(1);

    assertEquals(memePage, result);
  }

  @Test
  void save_UserPresent_SavesAndReturns() throws IOException {
    SaveMemeDto saveMemeDto = random.nextObject(SaveMemeDto.class);
    AuthUser authUser = mock(AuthUser.class);
    User author = random.nextObject(User.class);
    Template template = random.nextObject(Template.class);
    when(authUser.getUser()).thenReturn(author);
    when(entityManager.getReference(Template.class, saveMemeDto.getTemplateId()))
        .thenReturn(template);
    when(memeRepository.save(any(Meme.class))).then(returnsFirstArg());
    when(fileUploadService.uploadFile(any(MultipartFile.class), anyString())).thenReturn("path");

    Meme result = memeService.save(saveMemeDto, authUser, file);

    assertEquals(author, result.getAuthor());
    assertEquals(template, result.getTemplate());
  }

  @Test
  void save_UserNotPresent_SavedAndReturns() throws IOException {
    SaveMemeDto saveMemeDto = random.nextObject(SaveMemeDto.class);
    Template template = random.nextObject(Template.class);
    when(entityManager.getReference(Template.class, saveMemeDto.getTemplateId()))
        .thenReturn(template);
    when(memeRepository.save(any(Meme.class))).then(returnsFirstArg());
    when(fileUploadService.uploadFile(any(MultipartFile.class), anyString())).thenReturn("path");

    Meme result = memeService.save(saveMemeDto, null, file);

    assertEquals(template, result.getTemplate());
    assertNull(result.getAuthor());
  }
}
