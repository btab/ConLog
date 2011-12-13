package com.bluetheta.conlog

case class LogContext(name: String, parent: LogContext)

object RootContext extends LogContext("", null)
