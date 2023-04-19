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
import io.micrometer.core.instrument.Timer;
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
@SuppressWarnings({"java:S117", "java:S1602"})
public class ServiceController {

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

    private static final ExecutorService IO_EXECUTOR = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

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

    @Autowired
    private Timer healthTimer;

    @GetMapping(value = "/health-blocking", produces = APPLICATION_JSON_VALUE)
    @Timed(histogram = true)
    @Async("eventLoopN")
    public CompletableFuture<HealthStatus> asyncHealthBlocking() {
        logger.debug("async healthcheck");
        var start = System.nanoTime();
        healthCounter.increment();

        return RATE_LIMITER.executeCompletionStage(() ->
                CompletableFuture.supplyAsync(() -> retailService.readDataBlocking(100), IO_EXECUTOR)
                        .thenApply($ ->
                                new HealthStatus(
                                        $.toEpochSecond(ZoneOffset.of("-07:00")),
                                        serviceName,
                                        serviceVersion
                                ))).toCompletableFuture().whenComplete((__, ___) ->
                healthTimer.record(Duration.ofNanos(System.nanoTime() - start))
        );
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

    /**
     * 50%     18
     * 66%     23
     * 75%     25
     * 80%     28
     * 90%     37
     * 95%     58
     * 98%     72
     * 99%     80
     * 100%    309 (longest request)
     */
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
     * perf is much better than thread based requests
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
    //NOTE: response has to be CompletableFuture<>
    public CompletableFuture<HealthStatus> healthForBenchmarkWithEventLoop() {
        return CompletableFuture.supplyAsync(() ->
                new HealthStatus(
                        0,
                        serviceName,
                        serviceVersion
                ), IO_EXECUTOR);
    }

    /**
     * rs_health_timer_seconds_sum / rs_health_timer_seconds_count
     * 50%     44
     * 66%     56
     * 75%     64
     * 80%     67
     * 90%     76
     * 95%     83
     * 98%    126
     * 99%    166
     * 100%    212 (longest request)
     */
    @GetMapping("/health-benchmark-eventloopN")
    @Async("eventLoopN")
    @Timed(histogram = true)
    public CompletableFuture<HealthStatus> healthForBenchmarkWithEventLoopN() {
        var start = System.nanoTime();

        return CompletableFuture.supplyAsync(() -> new HealthStatus(
                0,
                serviceName,
                serviceVersion
        ), IO_EXECUTOR).whenComplete((__, ___) -> {
            healthTimer.record(Duration.ofNanos(System.nanoTime() - start));
        });
    }

    @RequestMapping("/api/build-info")
    public @ResponseBody
    CompletableFuture<ServiceBuildInfo> build() {
        return CompletableFuture.completedFuture(serviceBuildInfo);
    }
}
