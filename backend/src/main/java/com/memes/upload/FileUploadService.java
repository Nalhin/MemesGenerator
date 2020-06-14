package com.memes.upload;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

@Service
public class FileUploadService {
  final ServletContext servletContext;

  public FileUploadService(ServletContext servletContext) {
    this.servletContext = servletContext;
  }

  public String uploadFile(MultipartFile file) throws IOException {
    String fileLocation = new File("images").getAbsolutePath() + "/" + file.getOriginalFilename();


    file.transferTo(new File(fileLocation));

    return fileLocation;
  }
}
