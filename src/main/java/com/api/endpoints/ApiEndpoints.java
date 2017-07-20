package com.api.endpoints;

import com.api.domain.HealthStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
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

    @RequestMapping(value = "/putmoney", method = RequestMethod.PUT)
    public Map<String, String> putMoney() {
        return new HashMap<String, String>(){{
            put("username", "you");
            put("account", "9889");
        }};
    }
}
