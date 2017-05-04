package com.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by prayagupd
 * on 1/29/17.
 */

@SpringBootApplication
public class RESTApplication extends SpringBootServletInitializer {

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        PropertySourcesPlaceholderConfigurer propsConfig
                = new PropertySourcesPlaceholderConfigurer();
        propsConfig.setLocation(new ClassPathResource("build.properties"));
        propsConfig.setIgnoreResourceNotFound(true);
        propsConfig.setIgnoreUnresolvablePlaceholders(true);
        return propsConfig;
    }

    public static void main(String[] args) {
        SpringApplication.run(RESTApplication.class, args);
    }
}
