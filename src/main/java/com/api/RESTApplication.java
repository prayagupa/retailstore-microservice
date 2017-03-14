package com.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by prayagupd
 * on 1/29/17.
 */

@SpringBootApplication
public class RESTApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(RESTApplication.class, args);
    }
}
