package com.memes.meme;

import com.memes.auth.AuthUser;
import com.memes.meme.dto.MemeResponseDto;
import com.memes.meme.dto.SaveMemeDto;
import com.memes.shared.utils.CustomModelMapper;
import com.sun.istack.Nullable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/api/memes")
@Api(tags = "memes")
@RequiredArgsConstructor
public class MemeController {

  private final MemeService memeService;
  private final CustomModelMapper customModelMapper;

  @GetMapping(path = "/{memeId}")
  @ApiOperation(value = "Get meme by id")
  public MemeResponseDto getOneById(@PathVariable Long memeId) {
    return customModelMapper.map(memeService.getOneById(memeId), MemeResponseDto.class);
  }

  @GetMapping(path = "")
  @ApiOperation(value = "Get memes page")
  public Page<MemeResponseDto> getAll(@RequestParam(name = "page", defaultValue = "0") int page) {
    return memeService
        .findAll(page)
        .map(template -> customModelMapper.map(template, MemeResponseDto.class));
  }

  @PostMapping(
      path = "/save",
      consumes = {"multipart/form-data"})
  @ApiOperation(value = "Save meme")
  public MemeResponseDto save(
      @RequestPart SaveMemeDto saveMemeDto,
      @RequestPart MultipartFile file,
      @AuthenticationPrincipal @Nullable AuthUser authUser)
      throws IOException {
    return customModelMapper.map(
        memeService.save(saveMemeDto, authUser, file), MemeResponseDto.class);
  }
}
