package com.memes.meme;

import com.memes.auth.AuthUser;
import com.memes.meme.dto.SaveMemeDto;
import com.memes.template.Template;
import com.memes.upload.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemeService {

  private final MemeRepository memeRepository;
  private final FileUploadService fileUploadService;
  private final EntityManager entityManager;

  public Meme getOneById(Long id) {
    return memeRepository
        .findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Meme not found"));
  }

  public Page<Meme> findAll(int currentPage) {
    return memeRepository.findAll(PageRequest.of(currentPage, 10));
  }

  public Meme save(SaveMemeDto saveMemeDto, AuthUser authUser, MultipartFile file)
      throws IOException {
    Meme meme = new Meme();
    if (authUser != null) {
      meme.setAuthor(authUser.getUser());
    }
    meme.setTemplate(entityManager.getReference(Template.class, saveMemeDto.getTemplateId()));
    meme.setUrl(fileUploadService.uploadFile(file, UUID.randomUUID().toString() + ".jpg"));
    return memeRepository.save(meme);
  }
}
