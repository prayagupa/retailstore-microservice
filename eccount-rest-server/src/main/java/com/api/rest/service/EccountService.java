package com.api.rest.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EccountService {

    @Value("${some.properties}")
    private String someProps;

    public LocalDateTime readDataBlocking(int timeMillis) {
        System.out.println(someProps);
        try {
            Thread.sleep(timeMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return LocalDateTime.now();
    }

    public String getSomeProps() {
        return someProps;
    }
}
