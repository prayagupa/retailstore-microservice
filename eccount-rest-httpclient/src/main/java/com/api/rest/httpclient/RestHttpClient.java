package com.api.rest.httpclient;

import com.api.rest.schema.HealthStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * read - LIFO, FIFO pool
 * https://github.com/AsyncHttpClient/async-http-client/wiki/Connection-pooling#defaultchannelpool
 *
 * https://github.com/AsyncHttpClient/async-http-client/blob/master/client/src/main/java/org/asynchttpclient/netty/channel/DefaultChannelPool.java#L230
 *
 * TODOs
 * - retry
 * - circuit breaker
 */
//@AllArgsConstructor
public class RestHttpClient {

    /**
     * there is only one target host being used, so
     * total = max per host
     */
    private static final int MAX_CONNECTIONS_PER_TARGET_HOST = 100;

    private String baseUri;

    AsyncHttpClient httpClient = Dsl.asyncHttpClient(Dsl.config()
            .setMaxConnections(MAX_CONNECTIONS_PER_TARGET_HOST)
            .setMaxConnectionsPerHost(MAX_CONNECTIONS_PER_TARGET_HOST)
            .setPooledConnectionIdleTimeout(100)
            .setConnectionTtl(500)
    );

    private final static ObjectMapper encoder = new ObjectMapper();
    private final static Logger logger = LogManager.getLogger();

    public RestHttpClient(String baseUri) {
        this.baseUri = baseUri;
    }

    public CompletableFuture<HealthStatus> getHealthStatus(UUID requestId,
                                                           String sourceApi) {
        CompletableFuture<HealthStatus> response = httpClient.prepareGet(baseUri + "/health")
                .addHeader("x-request-id", requestId)
                .addHeader("x-source-api", sourceApi)
                .execute()
                .toCompletableFuture()
                .thenCompose(r -> {
                    try {
                        logger.error("requestId: {}, response: {}", requestId, r.getResponseBody());
                        var healthStatus = encoder.readValue(r.getResponseBody(), HealthStatus.class);
                        return CompletableFuture.completedFuture(healthStatus);
                    } catch (IOException e) {
                        logger.error("requestId: {}, error decoding response: ", requestId, e);
                        return CompletableFuture.failedFuture(new RuntimeException("Error decoding http response", e));
                    }
                });

        CompletableFuture<HealthStatus> httpResponse = new CompletableFuture<>();

        response.whenComplete((s, e) -> {
            if (e != null) {
                httpResponse.completeExceptionally(new RuntimeException("Unknown service error", e));
            } else {
                httpResponse.complete(s);
            }
        });

        return httpResponse;
    }

    public static void main(String[] args) throws InterruptedException {
        var client = new RestHttpClient("http://localhost:9099");
        var resp = client.getHealthStatus(UUID.randomUUID(), "test");
        Thread.sleep(1000);

        resp.whenComplete((s, e) -> {
            e.printStackTrace();
        });
    }
}
