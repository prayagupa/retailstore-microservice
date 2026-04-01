package com.api.rest.httpclient;

import com.api.rest.schema.HealthStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.client.RestClient;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Spring RestClient (Spring 6.1+, Spring-free, no Boot required)
 *
 * TODOs
 * - retry
 * - circuit breaker
 */
public class RestHttpClient {

    private static final Logger logger = LogManager.getLogger();
    private static final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    private final RestClient restClient;

    public RestHttpClient(String baseUri) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUri)
                .build();
    }

    public CompletableFuture<HealthStatus> getHealthStatus(UUID requestId, String sourceApi) {
        return CompletableFuture.supplyAsync(() -> {
            logger.debug("requestId: {}, fetching health status", requestId);
            return restClient.get()
                    .uri("/health")
                    .header("x-request-id", requestId.toString())
                    .header("x-source-api", sourceApi)
                    .retrieve()
                    .body(HealthStatus.class);
        }, executor).whenComplete((s, e) -> {
            if (e != null) {
                logger.error("requestId: {}, error fetching health status: ", requestId, e);
            } else {
                logger.debug("requestId: {}, response: {}", requestId, s);
            }
        });
    }

    public static void main(String[] args) throws InterruptedException {
        var client = new RestHttpClient("http://localhost:9099");
        var resp = client.getHealthStatus(UUID.randomUUID(), "test");
        Thread.sleep(1000);

        resp.whenComplete((s, e) -> {
            if (e != null) logger.error("error fetching health status: ", e);
            else System.out.println(s);
        });
    }
}
