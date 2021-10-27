package com.api.rest.service;

import com.api.rest.endpoints.UnitTestConfigForJUnit5;
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
public class EccountServiceTest {

    @Autowired
    EccountService eccountService;

    @Test
    public void testMe() {
        LocalDateTime localDateTime = eccountService.readDataBlocking(1);
        System.out.println(localDateTime);
        Assertions.assertNotNull(localDateTime);
    }
}
