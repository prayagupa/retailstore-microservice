package com.api.rest.endpoints;

import com.api.rest.schema.ApiBuildInfo;
import com.api.rest.schema.HealthStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by prayagupd
 * on 1/29/17.
 */

@RestController
public class ApiEndpoints {

    private final AtomicLong counter = new AtomicLong();

    @Value("${some.properties}")
    String someProperties;

    @RequestMapping("/health")
    public HealthStatus health() {
        return new HealthStatus(
                System.currentTimeMillis(),
                "rest-api",
                "1.0"
        );
    }

    @Autowired
    ApiBuildInfo apiBuildInfo;

    @RequestMapping("/apiBuildInfo")
    public ApiBuildInfo build() {
        return apiBuildInfo;
    }
}
