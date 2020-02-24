package com.api.rest.httpclient;

import com.api.rest.schema.HealthStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

//@AllArgsConstructor
public class RestHttpClient {

    private String baseUri;

    private AsyncHttpClient httpClient = new DefaultAsyncHttpClient(

    );

    private final static ObjectMapper encoder = new ObjectMapper();
    private final static Logger logger = LogManager.getLogger();

    public RestHttpClient(String baseUri) {
        this.baseUri = baseUri;
    }

    public CompletableFuture<HealthStatus> getHealthStatus(UUID requestId,
                                                           String sourceApi) {
        return httpClient.prepareGet(baseUri + "/health")
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
                        return CompletableFuture.failedFuture(e);
                    }
                });
    }

    public static void main(String[] args) {
        var response = new RestHttpClient("http://localhost:8080").getHealthStatus(
                UUID.randomUUID(), "test"
        );

        response.whenComplete((s, err) -> {
            System.out.println(s);
        });

    }
}
