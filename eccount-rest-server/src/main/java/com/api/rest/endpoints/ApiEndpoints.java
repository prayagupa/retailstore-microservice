package com.api.rest.endpoints;

import com.api.rest.schema.ApiBuildInfo;
import com.api.rest.schema.HealthStatus;
import com.api.rest.service.EccountService;
import io.micrometer.core.annotation.Timed;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneOffset;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by prayagupd
 * on 1/29/17.
 */

@RestController
@Timed
public class ApiEndpoints {

    private final static Logger logger = LogManager.getLogger();

    private final AtomicLong counter = new AtomicLong();

    @Value("${service.name}")
    private String serviceName;

    @Value("${service.version}")
    private String serviceVersion;

    @Value("${some.properties}")
    private String someProperties;

    @Autowired
    private ApiBuildInfo apiBuildInfo;

    @Autowired
    private EccountService eccountService;

    private ExecutorService executorService = Executors.newFixedThreadPool(100);

    @RequestMapping("/health")
//    @Async("requestExecutor")
    public CompletableFuture<HealthStatus> health() {

        logger.info("healthcheck");

        return CompletableFuture.supplyAsync(() -> eccountService.readDataBlocking(100), executorService)
                .thenApply($ -> {
                    return new HealthStatus(
                            $.toEpochSecond(ZoneOffset.of("-07:00")),
                            serviceName,
                            serviceVersion
                    );
                });
    }

    @RequestMapping("/health-sync")
    public HealthStatus healthSync() {

        logger.info("sync healthcheck");

        eccountService.readDataBlocking(100);

        return new HealthStatus(
                System.currentTimeMillis(),
                serviceName,
                serviceVersion
        );
    }

    @RequestMapping("/api/build-info")
    public @ResponseBody
    CompletableFuture<ApiBuildInfo> build() {
        return CompletableFuture.completedFuture(apiBuildInfo);
    }
}
