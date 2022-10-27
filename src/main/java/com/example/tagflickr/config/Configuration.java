package com.example.tagflickr.config;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.data.util.Predicates.negate;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    Docket docket() {
        return new Docket(DocumentationType.OAS_30).useDefaultResponseMessages(false)
                .produces(Stream.of("application/xml", "application/json").collect(Collectors.toSet())).select()
                .paths(negate(PathSelectors.regex("/error.*"))).build()
                .protocols(Stream.of("http", "https").collect(Collectors.toSet()));
    }
}
