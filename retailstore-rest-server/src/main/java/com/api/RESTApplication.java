package com.api;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
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

    @Bean(name = "eventLoop")
    public Executor eventLoop() {
        int singleWorkerThread = 1;

        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(singleWorkerThread);
        executor.setMaxPoolSize(singleWorkerThread);
        executor.setQueueCapacity(2000);
        executor.setThreadNamePrefix("RETAILSTORE-MICROSERVICE-EVENTLOOP-");
        executor.initialize();
        return executor;
    }

    @Bean(name = "eventLoopN")
    public Executor eventLoopN() {

        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(corePoolSize);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("RETAILSTORE-MICROSERVICE-EVENTLOOP-N-");
        executor.initialize();
        return executor;
    }

    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }

    @Bean
    public Counter healthCounter(MeterRegistry registry) {
        return Counter.builder("custom_health_counter")
                .description("Number of Health hits")
                .register(registry);
    }

    public static void main(String[] args) {
        SpringApplication.run(RESTApplication.class, args);
    }
}
