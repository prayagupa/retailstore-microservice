package com.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by prayagupd
 * on 1/29/17.
 */

@SpringBootApplication
public class RESTApplication {

    @Bean
    public WebMvcConfigurer crossOriginResourceSharing() {

        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/health")
                        .allowedOrigins("*");

                registry.addMapping("/putmoney")
                        .allowedOrigins("*")
                        .allowedMethods("PUT");
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(RESTApplication.class, args);
    }
}
