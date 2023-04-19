package com.api;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import io.micrometer.core.instrument.Timer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
@SuppressWarnings({"java:S1481", "java:S1854", "java:S1488"})
public class MicroserviceApplication extends SpringBootServletInitializer {

    public static final String CUSTOM_HEALTH_COUNTER = "rs_health_counter";
    public static final String CUSTOM_HEALTH_TIMER = "rs_health_timer";
    private static final Logger APP_LOGGER = LogManager.getLogger();
    private static final int REQUEST_QUEUE_CAPACITY = 2000;

    private static final int SINGLE_WORKER_THREAD = 1;
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

        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(SINGLE_WORKER_THREAD);
        executor.setMaxPoolSize(SINGLE_WORKER_THREAD);
        executor.setQueueCapacity(REQUEST_QUEUE_CAPACITY);
        executor.setThreadNamePrefix("RETAILSTORE-MICROSERVICE-EVENTLOOP-");
        executor.initialize();
        return executor;
    }

    @Bean(name = "eventLoopN")
    public Executor eventLoopN() {

        final ThreadPoolTaskExecutor executorN = new ThreadPoolTaskExecutor();
        int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
        executorN.setCorePoolSize(corePoolSize);
        executorN.setMaxPoolSize(corePoolSize);
        executorN.setQueueCapacity(REQUEST_QUEUE_CAPACITY);
        executorN.setThreadNamePrefix("RETAILSTORE-MICROSERVICE-EVENTLOOP-N-");
        executorN.initialize();
        return executorN;
    }

    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }

    @Bean
    public Counter healthCounter(MeterRegistry registry) {
        Counter register = Counter.builder(CUSTOM_HEALTH_COUNTER)
                .description("Number of Health hits")
                .tag("name", "retailstore")
                .register(registry);

        APP_LOGGER.info("{}: {}", CUSTOM_HEALTH_COUNTER, register.count());
        register.increment();
        APP_LOGGER.info("{}: {}", CUSTOM_HEALTH_COUNTER, register.count());
        return register;
    }

    @Bean
    public Timer healthTimer(MeterRegistry registry) {
        return Timer.builder(CUSTOM_HEALTH_TIMER)
                .description("Health time")
                .register(registry);
    }

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceApplication.class, args);
    }
}
