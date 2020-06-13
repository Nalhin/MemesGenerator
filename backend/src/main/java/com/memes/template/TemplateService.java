package com.memes.template;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TemplateService {

  private final TemplateRepository templateRepository;

  public Template getOneById(Long id) {
    return templateRepository
        .findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Template not found"));
  }

  public Page<Template> findAll(int currentPage) {
    return templateRepository.findAll(PageRequest.of(currentPage, 10));
  }

  public Template save(Template template) {
    return templateRepository.save(template);
  }
}
