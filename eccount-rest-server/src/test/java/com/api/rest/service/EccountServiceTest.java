package com.api.rest.service;

import com.api.rest.endpoints.UnitTestConfigForJUnit5;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
//@WebAppConfiguration()
@ContextConfiguration(classes = UnitTestConfigForJUnit5.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@SpringBootTest
public class EccountServiceTest {

    @Autowired
    EccountService eccountService;

    @Test
    public void testMe() {
        System.out.println("invoking eccountService: " + eccountService);
        LocalDateTime localDateTime = eccountService.readDataBlocking(1);
        System.out.println("invoked eccountService");
        System.out.println(localDateTime);
        Assertions.assertNotNull(localDateTime);
    }
}
