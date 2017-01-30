package com.api.endpoints;

import com.api.domain.HealthStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by prayagupd
 * on 1/29/17.
 */

@RestController
public class ApiEndpoints {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/health")
    public HealthStatus health() {
        return new HealthStatus(counter.incrementAndGet(), "some value", "I'm Running");
    }
}
