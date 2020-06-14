package com.memes.meme;

import com.memes.meme.dto.MemeResponseDto;
import com.memes.meme.dto.SaveMemeDto;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemeControllerTest {

  @Mock private MemeService memeService;
  private MemeController memeController;

  private final EasyRandom random = new EasyRandom();
  private final MultipartFile file = new MockMultipartFile("d", "d".getBytes());

  @BeforeEach
  void setUp() {
    memeController = new MemeController(memeService, new ModelMapper());
  }

  @Test
  void getById() {
    Meme returnedMeme = random.nextObject(Meme.class);
    when(memeService.getOneById(returnedMeme.getId())).thenReturn(returnedMeme);

    MemeResponseDto result = memeController.getOneById(returnedMeme.getId());

    assertEquals(returnedMeme.getId(), result.getId());
  }

  @Test
  void getAll() {
    Page<Meme> returnedMemes =
        new PageImpl<>(random.objects(Meme.class, 4).collect(Collectors.toList()));
    when(memeService.findAll(anyInt())).thenReturn(returnedMemes);

    Page<MemeResponseDto> result = memeController.getAll(1);

    assertEquals(returnedMemes.getSize(), result.getSize());
  }

  @Test
  void save() throws IOException {
    Meme savedMeme = random.nextObject(Meme.class);
    SaveMemeDto saveMemeDto = random.nextObject(SaveMemeDto.class);
    when(memeService.save(saveMemeDto, null, file)).thenReturn(savedMeme);

    MemeResponseDto result = memeController.save(saveMemeDto, file, null);

    assertEquals(savedMeme.getId(), result.getId());
  }
}
