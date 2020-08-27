package com.memes.test.matchers;

import org.springframework.test.web.servlet.ResultMatcher;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseHeaderMatchers {

  public ResultMatcher containsLocation(String partialLocation) {
    return mvcResult -> assertThat(mvcResult.getResponse().getHeader("Location")).contains(partialLocation);
  }

  public static ResponseHeaderMatchers responseHeader() {
    return new ResponseHeaderMatchers();
  }
}
