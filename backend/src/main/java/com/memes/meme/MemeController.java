package com.memes.meme;

import com.memes.auth.models.AuthUser;
import com.memes.meme.dto.MemeResponseDto;
import com.memes.meme.dto.SaveMemeDto;
import com.memes.shared.annotations.CurrentUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@Api(tags = "memes")
@RequiredArgsConstructor
public class MemeController {

  private final MemeService memeService;
  private final MemeMapper memeMapper;

  @GetMapping(path = "/memes/{memeId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Get meme by id")
  public MemeResponseDto getOneById(@PathVariable Long memeId) {
    return memeMapper.memeToMemeResponseDto(memeService.getOneById(memeId));
  }

  @GetMapping(path = "/memes", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Get memes page")
  public ResponseEntity<Page<MemeResponseDto>> getAll(
      @RequestParam(name = "page", defaultValue = "0") int page) {
    return ResponseEntity.ok(memeService.findAll(page).map(memeMapper::memeToMemeResponseDto));
  }

  @PostMapping(
      path = "/memes",
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Save meme")
  public ResponseEntity<MemeResponseDto> save(
      @RequestPart SaveMemeDto saveMemeDto,
      @RequestPart MultipartFile file,
      @CurrentUser AuthUser authUser) {

    Meme savedMeme = memeService.save(saveMemeDto, file, authUser);

    return ResponseEntity.created(URI.create("/" + savedMeme.getId()))
        .body(memeMapper.memeToMemeResponseDto(savedMeme));
  }
}
