package com.api.rest.endpoints;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.function.Function;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by prayagupd
 * on 1/29/17.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiEndpointsComponentTests {

    @Autowired
    private MockMvc httpEndpoint;

    @Test
    public void when_health_endpoint_is_hit_status_should_be_running() throws Exception {

        this.httpEndpoint.perform(get("/health")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.applicationVersion").value("1.0"));
    }


}
