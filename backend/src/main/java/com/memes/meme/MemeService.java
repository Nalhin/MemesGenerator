package com.memes.meme;

import com.memes.auth.AuthUser;
import com.memes.meme.dto.SaveMemeDto;
import com.memes.template.Template;
import com.memes.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class MemeService {

  private final MemeRepository memeRepository;
  private final EntityManager entityManager;

  public Meme getOneById(Long id) {
    return memeRepository
        .findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Meme not found"));
  }

  public Page<Meme> findAll(int currentPage) {
    return memeRepository.findAll(PageRequest.of(currentPage, 10));
  }

  public Meme save(SaveMemeDto saveMemeDto, AuthUser authUser) {
    Meme meme = new Meme();
    if (authUser != null) {
      meme.setAuthor(entityManager.getReference(User.class, authUser.getId()));
    }
    meme.setTemplate(entityManager.getReference(Template.class, saveMemeDto.getTemplateId()));
    return memeRepository.save(meme);
  }
}
