package com.api.endpoints

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.junit.Test
import org.junit.runner.RunWith
import org.scalatest.{FlatSpec, FunSuite, Matchers}
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, Configuration, Profile}
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.context.WebApplicationContext

/**
  * Created by prayagupd
  * on 1/29/17.
  */

@RunWith(classOf[SpringRunner])
@SpringBootTest
@AutoConfigureMockMvc
class ApiEndpointsIntegrationSpecs extends FunSuite with SpringTestContextManagement with Matchers {

  @Autowired val endpoint: MockMvc = null

  test("status is Running") {
    endpoint.perform(get("/health")).andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.status").value("I'm Running"))
  }
}

@Configuration
@Profile(Array("test"))
class TestConfiguration {

}
