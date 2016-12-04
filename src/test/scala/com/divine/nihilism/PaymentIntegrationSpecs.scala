package com.divine.nihilism

import java.io.File
import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

import akka.actor.ActorSystem
import com.zcode.springrestserver.web.controller.PayController
import org.apache.catalina.Context
import org.apache.catalina.startup.Tomcat
import org.scalatest.{BeforeAndAfterEach, FunSpec}
import spray.http._
import spray.client.pipelining._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

/**
  * Created by prayagupd
  * on 12/3/16.
  */

class PaymentIntegrationSpecs extends TestServer {
  import system.dispatcher

  def endpoints(ctx: Context): Unit = {
    Tomcat.addServlet(ctx, "payment", new PayController)
    ctx.addServletMapping("/payment", "payment")
  }

  describe("/payment") {
    it("responds with success message") {
      val pipeline: HttpRequest => Future[HttpResponse] = sendReceive
      val response: Future[HttpResponse] = pipeline(Get("http://localhost:9999/payment"))

      assert(Await.result(response, Duration("10 seconds")).status.intValue == 200)
      assert(Await.result(response, Duration("10 seconds")).status.value == "200 OK")
    }
  }
}
