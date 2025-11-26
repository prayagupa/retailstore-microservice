package com.api.rest.service;

import com.api.rest.UnitTestConfigForJUnit5;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UnitTestConfigForJUnit5.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SuppressWarnings({"java:S5786"})
public class RetailServiceTest {

    @Autowired
    RetailService retailService;

    @Test
    public void testReadDataBlocking() {
        System.out.println("invoking eccountService: " + retailService);
        LocalDateTime localDateTime = retailService.readDataBlocking(1);
        System.out.println("invoked eccountService: " + localDateTime);
        Assertions.assertNotNull(localDateTime);
    }
}
