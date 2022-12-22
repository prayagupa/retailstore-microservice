package com.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
@SuppressWarnings({"java:S1481", "java:S1854"})
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

    @Bean(name = "requestExecutor")
    public Executor requestExecutor() {

        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
        int singleWorkerThread = 1;
        executor.setCorePoolSize(singleWorkerThread);
        executor.setMaxPoolSize(singleWorkerThread);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("ECCOUNT-NIO-REST-API-");
        executor.initialize();
        return executor;
    }

    public static void main(String[] args) {
        SpringApplication.run(RESTApplication.class, args);
    }
}
