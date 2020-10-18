package com.memes.upload;

import org.springframework.core.io.ByteArrayResource;

public class FileNameAwareByteArrayResource extends ByteArrayResource {

  private final String fileName;

  public FileNameAwareByteArrayResource(byte[] byteArray, String fileName) {
    super(byteArray);
    this.fileName = fileName;
  }

  @Override
  public String getFilename() {
    return fileName;
  }
}
