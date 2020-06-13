package com.memes.config;

import com.memes.auth.AuthUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.security.Principal;
import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .ignoredParameterTypes(Principal.class, AuthUser.class)
        .select()
        .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(metadata())
        .securitySchemes(
            Collections.singletonList(new ApiKey("Bearer %token", "Authorization", "Header")));
  }

  private ApiInfo metadata() {
    return new ApiInfoBuilder()
        .title("Memes")
        .description("Memes rest api")
        .version("1.0.0")
        .license("MIT License")
        .build();
  }
}
