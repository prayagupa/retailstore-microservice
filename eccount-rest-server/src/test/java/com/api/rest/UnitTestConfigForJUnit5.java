package com.api.rest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"com.api"})
//this is needed to load file during test context
@PropertySource(value = "classpath:application-unit-test.properties", ignoreResourceNotFound = true)
public class UnitTestConfigForJUnit5 implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("setting applicationContext: " + applicationContext);
        this.applicationContext = applicationContext;
    }
}
