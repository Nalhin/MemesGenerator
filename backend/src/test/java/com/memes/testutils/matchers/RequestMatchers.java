package com.memes.testutils.matchers;

import org.assertj.core.api.ThrowableAssert;
import org.assertj.core.api.ThrowableAssertAlternative;
import org.springframework.web.util.NestedServletException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class RequestMatchers {

  private final ThrowableAssert.ThrowingCallable throwingCallable;

  public RequestMatchers(ThrowableAssert.ThrowingCallable throwingCallable) {
    this.throwingCallable = throwingCallable;
  }

  public static RequestMatchers assertThatRequest(ThrowableAssert.ThrowingCallable throwingCallable) {
    return new RequestMatchers(throwingCallable);
  }

  public ThrowableAssertAlternative<NestedServletException> throwsUnauthorizedException() {
    return assertThatExceptionOfType(NestedServletException.class)
        .isThrownBy(throwingCallable)
        .withMessageContaining("Access is denied");
  }
}
