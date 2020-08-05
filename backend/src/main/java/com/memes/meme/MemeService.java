package com.memes.meme;

import com.memes.auth.models.AuthUser;
import com.memes.meme.dto.SaveMemeDto;
import com.memes.template.TemplateRepository;
import com.memes.upload.FileUploadService;
import com.memes.upload.exceptions.ImageNotSavedException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemeService {

  private final MemeRepository memeRepository;
  private final TemplateRepository templateRepository;
  private final FileUploadService fileUploadService;

  public Meme getOneById(Long id) {
    return memeRepository
        .findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Meme not found"));
  }

  public Page<Meme> findAll(int currentPage) {
    return memeRepository.findAll(PageRequest.of(currentPage, 10));
  }

  @Transactional
  public Meme save(SaveMemeDto saveMemeDto, MultipartFile file, AuthUser authUser) {
    Meme meme = new Meme();
    authUser.getUser().ifPresent(meme::setAuthor);
    meme.setTemplate(templateRepository.getOne(saveMemeDto.getTemplateId()));
    try {
      String uploadUrl = fileUploadService.uploadFile(file, UUID.randomUUID().toString() + ".jpg");
      meme.setFilename(uploadUrl);
    } catch (ImageNotSavedException exception) {
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Image could not be saved.");
    }
    return memeRepository.save(meme);
  }
}
