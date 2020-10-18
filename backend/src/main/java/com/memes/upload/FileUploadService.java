package com.memes.upload;

import com.memes.upload.exceptions.ImageNotSavedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileUploadService {

  @Value("${images.upload-url}")
  private String fileUploadUrl;

  private final RestTemplate restTemplate;

  public FileUploadService(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  public String uploadFile(MultipartFile file, String fileName) throws ImageNotSavedException {
    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    try {
      ByteArrayResource contentsAsResource =
          new FileNameAwareByteArrayResource(file.getBytes(), fileName);
      body.add("image", contentsAsResource);
    } catch (IOException exception) {
      throw new ImageNotSavedException();
    }

    ResponseEntity<String> response =
        restTemplate.postForEntity(fileUploadUrl, new HttpEntity<>(body), String.class);

    if (!response.getStatusCode().is2xxSuccessful()) {
      throw new ImageNotSavedException();
    }

    return fileName;
  }
}
