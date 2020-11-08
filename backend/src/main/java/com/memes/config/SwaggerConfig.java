package com.memes.config;

import com.fasterxml.classmate.TypeResolver;
import com.memes.security.model.AuthenticatedUser;
import com.memes.common.dto.UnauthorizedExceptionDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.security.Principal;
import java.util.Collections;

@Configuration
@EnableOpenApi
public class SwaggerConfig {
  @Bean
  public Docket api(TypeResolver typeResolver) {
    return new Docket(DocumentationType.OAS_30)
        .ignoredParameterTypes(Principal.class, AuthenticatedUser.class)
        .select()
        .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
        .paths(PathSelectors.any())
        .build()
        .useDefaultResponseMessages(false)
        .additionalModels(typeResolver.resolve(UnauthorizedExceptionDto.class))
        .apiInfo(metadata())
        .securitySchemes(Collections.singletonList(apiKey()));
  }

  private ApiInfo metadata() {
    return new ApiInfoBuilder()
        .title("Memes")
        .description("Memes App REST API")
        .version("1.0.0")
        .license("MIT License")
        .build();
  }

  private ApiKey apiKey() {
    return new ApiKey("Bearer", "Authorization", "Header");
  }
}
