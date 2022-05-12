package com.api.rest.httpclient;

import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import com.api.rest.httpclient.cdc.RestServiceProvider;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Random;
import static org.assertj.core.api.Assertions.assertThat;

public class RestServiceProviderTest extends RestServiceProvider {

    @Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2(
            "service_contract_consumer",
            "localhost",
            getAvailablePort(),
            this
    );

    @Test
    @PactVerification()
    public void givenGet_whenSendRequest_shouldReturn200WithProperHeaderAndBody() {
        // when
        ResponseEntity<String> response = new RestTemplate()
                .getForEntity(mockProvider.getUrl() + "/eccount", String.class);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getHeaders().get("Content-Type").contains("application/json")).isTrue();
        assertThat(response.getBody()).contains("condition", "true", "name", "tom");

        // and
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        String jsonBody = "{\"name\": \"Michael\"}";

        //post test
        // when
//        ResponseEntity<String> postResponse = new RestTemplate()
//                .exchange(mockProvider.getUrl() + "/eccount", HttpMethod.POST,
//                        new HttpEntity<>(jsonBody, httpHeaders), String.class);

        // then
//        assertThat(postResponse.getStatusCode().value()).isEqualTo(201);
    }

    private static boolean isFree(int port) {
        try {
            new ServerSocket(port).close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static int getAvailablePort() {
        return new Random()
                .ints(6000, 9000)
                .filter(RestServiceProviderTest::isFree)
                .findFirst()
                .orElse(8080);
    }

}
