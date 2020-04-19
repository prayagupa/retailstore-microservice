package com.api.rest.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EccountService {

    public LocalDateTime readDataBlocking(int timeMillis) {
        try {
            Thread.sleep(timeMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return LocalDateTime.now();
    }
}
