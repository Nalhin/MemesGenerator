package com.memes.meme;

import com.memes.auth.models.AnonymousUser;
import com.memes.auth.models.AuthUser;
import com.memes.meme.dto.MemeResponseDto;
import com.memes.meme.dto.SaveMemeDto;
import com.memes.user.UserMapperImpl;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemeControllerTest {

  @Mock private MemeService memeService;

  @InjectMocks private MemeController memeController;

  private final EasyRandom random = new EasyRandom();
  private final MultipartFile file = new MockMultipartFile("d", "d".getBytes());

  @BeforeEach
  void setUp() {
    memeController = new MemeController(memeService, new MemeMapperImpl(new UserMapperImpl()));
  }

  @Test
  void getById_MemeFound_ReturnsMeme() {
    Meme returnedMeme = random.nextObject(Meme.class);
    when(memeService.getOneById(returnedMeme.getId())).thenReturn(returnedMeme);

    MemeResponseDto result = memeController.getOneById(returnedMeme.getId());

    assertEquals(returnedMeme.getId(), result.getId());
  }

  @Test
  void getAll_MemesPresent_ReturnsSameSize() {
    Page<Meme> returnedMemes =
        new PageImpl<>(random.objects(Meme.class, 4).collect(Collectors.toList()));
    when(memeService.findAll(anyInt())).thenReturn(returnedMemes);

    ResponseEntity<Page<MemeResponseDto>> result = memeController.getAll(1);

    assertNotNull(result.getBody());
    assertEquals(returnedMemes.getSize(), result.getBody().getSize());
  }

  @Test
  void save_OperationSuccessful_ReturnsSavedMeme() {
    Meme savedMeme = random.nextObject(Meme.class);
    SaveMemeDto saveMemeDto = random.nextObject(SaveMemeDto.class);
    AnonymousUser anonymousUser = new AnonymousUser();
    when(memeService.save(saveMemeDto, file, anonymousUser)).thenReturn(savedMeme);

    ResponseEntity<MemeResponseDto> result = memeController.save(saveMemeDto, file, anonymousUser);

    assertNotNull(result.getBody());
    assertEquals(savedMeme.getId(), result.getBody().getId());
  }
}
