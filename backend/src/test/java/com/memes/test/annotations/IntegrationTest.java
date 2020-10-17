package com.memes.test.annotations;

import org.junit.jupiter.api.Tag;
import org.springframework.test.context.ActiveProfiles;

@Tag("IntegrationTest")
@ActiveProfiles("integration")
public @interface IntegrationTest {
}
