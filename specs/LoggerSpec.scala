package com.bluetheta.conlog

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers
import scala.collection.mutable.{ArrayBuffer => MSeq}

class LoggerSpec extends WordSpec with ShouldMatchers {
  def logger = new Logger
  
  "ctx should act as a stack" in {
    val events = MSeq[LogEvent]()
    val logger = new Logger { override def publish(event: LogEvent) = events += event }
    
    logger.ctx should equal (RootContext)
    
    logger.pushContext("ctx1")
    val ctx1 = logger.ctx
    ctx1.name should equal ("ctx1")
    ctx1.parent should equal (RootContext)
    
    logger.pushContext("ctx2")
    val ctx2 = logger.ctx
    ctx2.name should equal ("ctx2")
    ctx2.parent should equal (ctx1)
    
    logger.popContext("ctx2 summary")
    logger.ctx should equal (ctx1)
    
    logger.popContext()
    logger.ctx should equal (RootContext)
    
    evaluating { logger.popContext() } should produce [NoSuchElementException]
    
    val expectedEvents = Seq(
      LogEvent('contextPush, "",             ctx1,        Nowhere),
      LogEvent('contextPush, "",             ctx2,        Nowhere),
      LogEvent('contextPop,  "ctx2 summary", ctx1,        Nowhere),
      LogEvent('contextPop,  "",             RootContext, Nowhere)
    )
    events should equal (expectedEvents)
  }
  
  "logging should respect the current context" in {
    val events = MSeq[LogEvent]()
    val logger = new Logger { override def publish(event: LogEvent) = events += event }
    
    logger.info("foo")
    logger.pushContext("ctx1")
    val ctx1 = logger.ctx
    logger.info("bar")
    
    val expectedEvents = Seq(
      LogEvent('info,        "foo",          RootContext, Nowhere),
      LogEvent('contextPush, "",             ctx1,        Nowhere),
      LogEvent('info,        "bar",          ctx1,        Nowhere)
    )
    events should equal (expectedEvents)
  }
}
