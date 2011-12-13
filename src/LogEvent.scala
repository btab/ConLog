package com.bluetheta.conlog

case class LogEvent(level: Symbol, msg: String, ctx: LogContext, loc: LogLocator) {
  lazy val location = loc.location
}
