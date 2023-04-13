package com.api.rest.endpoints;

import com.api.rest.schema.HealthStatus;
import com.api.rest.schema.ServiceBuildInfo;
import com.api.rest.service.RetailService;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@SuppressWarnings("java:S117")
public class ServiceEndpoints {

    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private MeterRegistry registry;

    @SuppressWarnings("java:S1068")
    private final AtomicLong counter = new AtomicLong();

    @Value("${service.name}")
    private String serviceName;

    @Value("${service.version}")
    private String serviceVersion;

    @Value("${some.properties}")
    private String someProperties;

    @Autowired
    private ServiceBuildInfo serviceBuildInfo;

    @Autowired
    private RetailService retailService;

    private ExecutorService executorService = Executors.newFixedThreadPool(100);

    private static final int REFRESH_PERIOD = 60 * 1000;

    private static final RateLimiter RATE_LIMITER = RateLimiterRegistry.of(
            RateLimiterConfig.custom()
                    .limitRefreshPeriod(Duration.ofMillis(REFRESH_PERIOD))
                    .limitForPeriod(10000)
                    .timeoutDuration(Duration.ofMillis(25))
                    .build()
    ).rateLimiter("health-limiter");

    @Autowired
    private Counter healthCounter;

    @GetMapping(value = "/health-blocking", produces = APPLICATION_JSON_VALUE)
    @Timed(histogram = true)
    @Async("eventLoopN")
    public CompletableFuture<HealthStatus> asyncHealthBlocking() {
        logger.debug("async healthcheck");
        healthCounter.increment();

        return RATE_LIMITER.executeCompletionStage(() ->
                CompletableFuture.supplyAsync(() -> retailService.readDataBlocking(100), executorService)
                        .thenApply($ ->
                                new HealthStatus(
                                        $.toEpochSecond(ZoneOffset.of("-07:00")),
                                        serviceName,
                                        serviceVersion
                                ))).toCompletableFuture();
    }

    @GetMapping("/health-blocking-sync")
    @Timed(histogram = true)
    public HealthStatus healthSync() {

        logger.debug("sync healthcheck");

        LocalDateTime localDateTime = retailService.readDataBlocking(100);

        return new HealthStatus(
                localDateTime.toEpochSecond(ZoneOffset.of("-07:00")),
                serviceName,
                serviceVersion
        );
    }

    @GetMapping("/health-benchmark")
    @Timed(histogram = true)
    public HealthStatus healthForBenchmark() {
        return new HealthStatus(
                0,
                serviceName,
                serviceVersion
        );
    }

    /**
     * perf is much better than thread based health-benchmark
     * Percentage of the requests served within a certain time (ms)
     * 50%      4
     * 66%      5
     * 75%      5
     * 80%      5
     * 90%      8
     * 95%      9
     * 98%     11
     * 99%     13
     * 100%     52 (longest request)
     */
    @GetMapping("/health-benchmark-eventloop")
    @Async("eventLoop")
    @Timed(histogram = true)
    public HealthStatus healthForBenchmarkWithEventLoop() {
        return new HealthStatus(
                0,
                serviceName,
                serviceVersion
        );
    }

    /**
     * Percentage of the requests served within a certain time (ms)
     * 50%      4
     * 66%      4
     * 75%      4
     * 80%      5
     * 90%      5
     * 95%      7
     * 98%      8
     * 99%      9
     * 100%     19 (longest request)
     */
    @GetMapping("/health-benchmark-eventloopN")
    @Async("eventLoopN")
    @Timed(histogram = true)
    public HealthStatus healthForBenchmarkWithEventLoopN() {
        return new HealthStatus(
                0,
                serviceName,
                serviceVersion
        );
    }

    @RequestMapping("/api/build-info")
    public @ResponseBody
    CompletableFuture<ServiceBuildInfo> build() {
        return CompletableFuture.completedFuture(serviceBuildInfo);
    }
}
