package edu.fudan.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fdse
 */
@Configuration
public class SwaggerConfig {

    @Value("${swagger.controllerPackage}")
    private String controllerPackagePath;

    private static final Logger LOGGER = LoggerFactory.getLogger(SwaggerConfig.class);

    @Bean
    public GroupedOpenApi publicApi() {
        SwaggerConfig.LOGGER.info("[createRestApi][create][controllerPackagePath: {}]", controllerPackagePath);
        return GroupedOpenApi.builder()
                .group(controllerPackagePath)
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Springboot builds the API documentation with swagger")
                        .description("Simple and elegant restful style")
                        .contact(new Contact().url("https://github.com/MicroserviceCongestion/train-ticket"))
                        .version("1.0")
                );
    }

}
