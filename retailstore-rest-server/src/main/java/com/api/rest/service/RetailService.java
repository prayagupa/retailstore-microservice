package com.api.rest.service;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Getter
public final class RetailService {

    @Value("${some.properties}")
    private String someProps;

    private static final Logger logger = LogManager.getLogger();

    public LocalDateTime readDataBlocking(final int timeMillis) {
        logger.info(someProps);
        try {
            Thread.sleep(timeMillis);
        } catch (InterruptedException e) {
            logger.error("Error", e);
            Thread.currentThread().interrupt();
        }

        return LocalDateTime.now();
    }
}
