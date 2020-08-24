package com.memes.test.matchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.memes.test.utils.AuthorizationUtils;
import org.assertj.core.api.SoftAssertions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseBodyMatchers {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public <T> ResultMatcher containsObjectAsJson(Object expectedObject, Class<T> targetClass) {
    return mvcResult -> {
      String json = mvcResult.getResponse().getContentAsString();
      T actualObject = objectMapper.readValue(json, targetClass);
      assertThat(actualObject).isEqualToComparingFieldByField(expectedObject);
    };
  }

  public ResultMatcher containsValidationErrors(String... fields) {
    return mvcResult -> {
      String json = mvcResult.getResponse().getContentAsString();
      ArrayNode validationExceptionDto = (ArrayNode) objectMapper.readTree(json).get("errors");

      List<String> fieldErrors =
          StreamSupport.stream(
                  Spliterators.spliteratorUnknownSize(
                      validationExceptionDto.elements(), Spliterator.ORDERED),
                  false)
              .map(e -> e.get("field").asText())
              .collect(Collectors.toList());

      SoftAssertions.assertSoftly(
          softly -> Arrays.stream(fields).forEach(f -> softly.assertThat(fieldErrors).contains(f)));
    };
  }

  public ResultMatcher containsValidJWT(){
    return mvcResult -> {
      String json = mvcResult.getResponse().getContentAsString();
      String token =  objectMapper.readTree(json).get("token").textValue();
      assertThat(AuthorizationUtils.validateToken(token)).isTrue();
    };
  }

  public static ResponseBodyMatchers responseBody() {
    return new ResponseBodyMatchers();
  }
}
