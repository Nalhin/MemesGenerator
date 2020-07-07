package com.memes.meme;

import com.memes.auth.AuthUser;
import com.memes.meme.dto.MemeResponseDto;
import com.memes.meme.dto.SaveMemeDto;
import com.sun.istack.Nullable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
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
  private final ModelMapper modelMapper;

  @GetMapping(path = "/{memeId}")
  @ApiOperation(value = "Get meme by id")
  public @ResponseBody MemeResponseDto getOneById(@PathVariable Long memeId) {
    return modelMapper.map(memeService.getOneById(memeId), MemeResponseDto.class);
  }

  @GetMapping(path = "")
  @ApiOperation(value = "Get memes page")
  public @ResponseBody Page<MemeResponseDto> getAll(
      @RequestParam(name = "page", defaultValue = "0") int page) {
    return memeService
        .findAll(page)
        .map(template -> modelMapper.map(template, MemeResponseDto.class));
  }

  @PostMapping(
      path = "/save",
      consumes = {"multipart/form-data"})
  @ApiOperation(value = "Save meme")
  public @ResponseBody MemeResponseDto save(
      @RequestPart SaveMemeDto saveMemeDto,
      @RequestPart MultipartFile file,
      @AuthenticationPrincipal @Nullable AuthUser authUser)
      throws IOException {
    return modelMapper.map(memeService.save(saveMemeDto, authUser, file), MemeResponseDto.class);
  }
}
