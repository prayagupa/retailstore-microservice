package com.api.endpoints

import eventstream.events.BaseEvent
import org.scalatest.FunSuite

/**
  * Created by prayagupd
  * on 2/1/17.
  */
class ToEventSpecs extends FunSuite {

  val toEvent = new EventFinder()

  test("returns the eventType") {
    toEvent.EVENTS_PACKAGE = "com.api.endpoints"
    val eventType = toEvent.eventType("TestEvento")
    assert(eventType.isPresent)
    assert(eventType.get() == classOf[TestEvento])
  }
}

class TestEvento extends BaseEvent {

}