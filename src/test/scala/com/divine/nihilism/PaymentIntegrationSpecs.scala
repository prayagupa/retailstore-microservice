package com.divine.nihilism

import com.zcode.springrestserver.web.controller.PayController
import org.apache.catalina.Context
import org.apache.catalina.startup.Tomcat
import spray.client.pipelining._
import spray.http._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

/**
  * Created by prayagupd
  * on 12/3/16.
  */

class PaymentIntegrationSpecs extends TestServer {
  import system.dispatcher

  def endpoints(context: Context): Unit = {
    Tomcat.addServlet(context, "payment", new PayController)
    context.addServletMapping("/payment", "payment")

//    val contextt = tomcat.addWebapp("/", new File("src/main/webapp").getAbsolutePath)
//    contextt.setAltDDName("src/main/webapp/WEB-INF/web.xml")

    //val config = new ContextConfig
    //config.setDefaultWebXml("src/main/webapp/WEB-INF/web.xml")
    //context.addLifecycleListener(config)
  }

  describe("/items") {
    it("responds with success message") {
      val pipeline: HttpRequest => Future[HttpResponse] = sendReceive
      val response: Future[HttpResponse] = pipeline(Get("http://localhost:9999/payment"))

      assert(Await.result(response, Duration("1 seconds")).status.intValue == 200)
      assert(Await.result(response, Duration("1 seconds")).status.value == "200 OK")

      val bodyString = Await.result(response, Duration("1 seconds")).entity.asString.trim
      assert(bodyString == "Paid")
    }
  }
}
