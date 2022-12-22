package com.api.rest.endpoints;

//import org.junit.Test;
import com.api.rest.UnitTestConfigForJUnit5;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
//import org.junit.runner.RunWith;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by prayagupd
 * on 1/29/17.
 */

//NOTE in JUnit4
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Profile("ut")
//@AutoConfigureMockMvc

//Junit5
@ExtendWith(SpringExtension.class)
//@WebAppConfiguration()
//@ContextConfiguration(classes = UnitTestConfigForJUnit5.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@SuppressWarnings({"java:S5786"})
public class ServiceEndpointsComponentTest {

    private MockMvc httpEndpoint;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        System.out.println("ApiEndpointsComponentTest: setup");
        this.httpEndpoint = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void when_health_endpoint_is_hit_status_should_be_running() throws Exception {

        MvcResult mvcResult = this.httpEndpoint.perform(get("/health")
                .contentType("application/json"))
                .andExpect(request().asyncStarted())
                .andDo(MockMvcResultHandlers.log())
                .andReturn();

        httpEndpoint.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.applicationVersion").value("1.0"));
    }

}
