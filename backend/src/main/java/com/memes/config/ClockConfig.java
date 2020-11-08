package com.memes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class ClockConfig {

  @Bean
  Clock clock() {
    return Clock.systemUTC();
  }
}
