package com.bluetheta.conlog

import org.scalatest.WordSpec
import org.scalatest.matchers.ShouldMatchers

class LogEventSpec extends WordSpec with ShouldMatchers {
  "location should return the location of the supplied LogLocator" in {
    val locator = new LogLocator { def location = "foo" }
    LogEvent(null, null, null, locator).location should equal ("foo")
  }
}
