package com.memes.common.plugins;

import com.memes.common.annotations.Authenticated;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
public class AuthenticatedOperationBuilderPlugin implements OperationBuilderPlugin {

  @Override
  public void apply(OperationContext context) {
    Optional<Authenticated> methodAnnotation = context.findAnnotation(Authenticated.class);
    if (methodAnnotation.isPresent()) {
      Authenticated annotation = methodAnnotation.get();
      context
          .operationBuilder()
          .responses(
              Collections.singletonList(
                  new ResponseBuilder()
                      .code(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                      .description("Unauthorized access")
                      .representation(MediaType.APPLICATION_JSON)
                      .apply(
                          r ->
                              r.model(
                                  m ->
                                      m.referenceModel(
                                          ref ->
                                              ref.key(
                                                  key ->
                                                      key.qualifiedModelName(
                                                          name ->
                                                              name.namespace("com.memes.shared")
                                                                  .name(
                                                                      "UnauthorizedExceptionDto"))))))
                      .build()))
          .authorizations(
              Collections.singletonList(
                  new SecurityReference(
                      annotation.reference(),
                      Arrays.stream(annotation.authScopes())
                          .map(scope -> new AuthorizationScope(scope.scope(), scope.description()))
                          .toArray(AuthorizationScope[]::new))));
    }
  }

  @Override
  public boolean supports(DocumentationType delimiter) {
    return SwaggerPluginSupport.pluginDoesApply(delimiter);
  }
}
