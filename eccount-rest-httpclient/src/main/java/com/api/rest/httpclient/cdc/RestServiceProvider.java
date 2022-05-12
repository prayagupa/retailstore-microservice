package com.api.rest.httpclient.cdc;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;

import java.util.HashMap;
import java.util.Map;

public class RestServiceProvider {

    @Pact(consumer = "service_contract_consumer")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return builder
                .given("test GET")
                .uponReceiving("GET REQUEST")
                .path("/eccount")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body("{\"condition\": true, \"name\": \"tom\"}")

//                .given("test POST")
//                .uponReceiving("POST REQUEST")
//                .method("POST")
//                .headers(headers)
//                .body("{\"name\": \"Michael\"}")
//                .path("/pact")
//                .willRespondWith()
//                .status(201)
                .toPact();
    }
}
