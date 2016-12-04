package com.divine.nihilism

import java.io.File

import akka.actor.ActorSystem
import com.zcode.springrestserver.web.controller.PayController
import org.apache.catalina.Context
import org.apache.catalina.startup.Tomcat
import org.scalatest.{BeforeAndAfterEach, FunSpec}

/**
  * Created by prayagupd
  * on 12/3/16.
  */

abstract class TestServer extends FunSpec with BeforeAndAfterEach {

  var tomcat : Tomcat = _

  implicit val system = ActorSystem()
  import system.dispatcher

  override protected def beforeEach(): Unit = {
    tomcat = new Tomcat
    tomcat.setBaseDir(".")
    tomcat.setPort(9999)

    val ctx = tomcat.addContext("/", new File(".").getAbsolutePath());

    endpoints(ctx)

    tomcat.init()
    tomcat.start()
  }

  def endpoints(ctx: Context)

  override protected def afterEach(): Unit = tomcat.stop()
}
