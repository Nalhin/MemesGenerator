package com.memes.test.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RequestUtils {

  private static final ObjectMapper mapper = new ObjectMapper();

  public static String asJSON(Object obj) throws JsonProcessingException {
    return  mapper.writeValueAsString(obj);
  }
}
