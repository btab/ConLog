package com.bluetheta.conlog.subscribers

import com.bluetheta.conlog._

import java.io.{OutputStream, PrintStream}

object SimpleLogReporter {
  val symbolsByLevel = Map('debug -> "+", 'error -> "*", 'fatal -> "!", 'info -> "-", 'warn -> "~")
}

case class SimpleLogReporter(_out: OutputStream, _levels: Set[Symbol] = Logger.levels) extends LogSubscriber {
  private val out = new PrintStream(_out)
  private val levels = _levels ++ Set('contextPush, 'contextPop)
  
  def close = out.close
  
  private var ctx = RootContext
  private var indent = ""
  
  def notify(log: Logger, event: LogEvent) = if (levels.contains(event.level)) {
    val msg = (if (event.loc == Nowhere) "" else event.location + " => ") + event.msg
    
    val report = event.level match {
      case 'contextPop  => if (event.msg.isEmpty) "" else "> " + msg
      case 'contextPush => event.ctx.name
      case _            => SimpleLogReporter.symbolsByLevel(event.level) + " " + event.msg
    }
    
    if (report.nonEmpty) out.println(indent + report)
        
    if (event.level == 'contextPop) indent += " "
    else if (event.level == 'contextPush) indent = indent.slice(0, indent.size - 1)
  }
}
