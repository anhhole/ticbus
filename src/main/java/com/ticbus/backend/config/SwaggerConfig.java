package com.ticbus.backend.config;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author AnhLH
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket api() {

    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.ticbus.backend.controller"))
        .paths(PathSelectors.regex("/.*"))
        .build()
        .apiInfo(apiInfo())
        .securitySchemes(Arrays.asList(apiKey()));
  }

  private ApiKey apiKey() {
    return new ApiKey("Bearer", "Authorization",
        "header"); //`apiKey` is the name of the APIKey, `Authorization` is the key in the request header
  }

  private ApiInfo apiInfo() {

    return new ApiInfoBuilder()
        .title("TicBus API")
        .description("Backed by AnhLH .")
        .termsOfServiceUrl("")
        .contact(new Contact(
            "Anh Le ho",
            "",
            "anh.le@mofavn.vn"))
        .license("Apache License Version 2.0")
        .licenseUrl("")
        .version("1.0.0")
        .build();
  }
}
