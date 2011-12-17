package com.bluetheta.conlog.subscribers

import com.bluetheta.conlog._

import java.io.ByteArrayOutputStream
import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers

class SimpleLogReporterSpec extends WordSpec with ShouldMatchers {
  def output(levels: Set[Symbol] = Logger.levels) = {
    val logger = new Logger
    val outStream = new ByteArrayOutputStream
    val reporter = SimpleLogReporter(outStream, levels)
    logger.subscribe(reporter)
    simulateLogging(logger)
    outStream.toString
  }
  
  def simulateLogging(logger: Logger) = {
    logger.debug("starting up")
    
    logger.pushContext("scanning")
    logger.warn("rebuilding cache")
    logger.info("progress: 25/200")
    logger.fatal("xxxxxx")
    logger.popContext("scan completed succcessfully")
    
    logger.pushContext("assessing")
    val errorLocation = new LogLocator { def location = "files/file12:22" }
    logger.error("file 12 has problems", errorLocation)
    logger.popContext()
  }
  
  "notify" should {
    "pretty-print the supplied log events as expected" in {
      val expectedOutput = """+ starting up
scanning
 ~ rebuilding cache
 - progress: 25/200
 ! xxxxxx
 > scan completed succcessfully
assessing
 * files/file12:22 => file 12 has problems
"""
      output() should equal (expectedOutput)
    }
    
    "ignore non-context events whose levels are not explicitly included" in {
      val expectedOutput = """scanning
 - progress: 25/200
 > scan completed succcessfully
assessing
"""
      output(Set('info)) should equal (expectedOutput)
    }
  }
}
