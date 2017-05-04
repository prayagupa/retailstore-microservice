package com.api.endpoints

import org.junit.runner.RunWith
import org.scalatest.{FunSuite, Matchers}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.{Configuration, Profile}
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.{jsonPath, status}

/**
  * Created by prayagupd
  * on 1/29/17.
  */

@RunWith(classOf[SpringRunner])
@SpringBootTest
@AutoConfigureMockMvc
class ApiEndpointsIntegrationSpecs extends FunSuite with SpringTestContextManagement with Matchers {

  @Autowired val mockMvc: MockMvc = null

  test("status is Running") {
    mockMvc.perform(get("/health")).andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.status").value("I'm Running"))
  }
}

@Configuration
@Profile(Array("test"))
class TestConfiguration {

}
