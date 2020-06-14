package com.memes.meme;

import com.memes.auth.AuthUser;
import com.memes.meme.dto.MemeResponseDto;
import com.memes.meme.dto.SaveMemeDto;
import com.sun.istack.Nullable;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/memes")
@Api(tags = "memes")
@RequiredArgsConstructor
public class MemeController {

  private final MemeService memeService;
  private final ModelMapper modelMapper;

  @GetMapping(path = "/{templateId}")
  public @ResponseBody MemeResponseDto getOneById(@PathVariable Long templateId) {
    return modelMapper.map(memeService.getOneById(templateId), MemeResponseDto.class);
  }

  @GetMapping(path = "")
  public @ResponseBody Page<MemeResponseDto> getAll(
      @RequestParam(name = "page", defaultValue = "0") int page) {
    return memeService
        .findAll(page)
        .map(template -> modelMapper.map(template, MemeResponseDto.class));
  }

  @PostMapping(path = "/save")
  public @ResponseBody MemeResponseDto save(
      @RequestBody SaveMemeDto saveMemeDto, @AuthenticationPrincipal @Nullable AuthUser authUser) {
    return modelMapper.map(memeService.save(saveMemeDto, authUser), MemeResponseDto.class);
  }
}
