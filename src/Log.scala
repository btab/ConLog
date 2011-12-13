package com.bluetheta.conlog

import scala.collection.mutable.Publisher

class Log extends Publisher[LogEvent] {
  var ctx: LogContext = RootContext
  
  def popContext = 
    if (ctx == RootContext)
      throw new Exception("already at root context")
    else { 
      ctx = ctx.parent
      log('context, "", ctx, Nowhere)
    }
    
  def pushContext(name: String) = {
    ctx = LogContext(name, ctx)
    log('context, "", ctx, Nowhere)
  }
  
  private def log(level: Symbol, msg: String, ctx: LogContext, loc: LogLocator) = {
    publish(LogEvent(level, msg, ctx, loc))
  }
  
  def debug(msg: String) = log('debug, msg, ctx, Nowhere)
  def error(msg: String) = log('error, msg, ctx, Nowhere)
  def fatal(msg: String) = log('fatal, msg, ctx, Nowhere)
  def info (msg: String) = log('info,  msg, ctx, Nowhere)
  def warn (msg: String) = log('warn,  msg, ctx, Nowhere)
  
  def debug(msg: String, loc: LogLocator) = log('debug, msg, ctx, loc)
  def error(msg: String, loc: LogLocator) = log('error, msg, ctx, loc)
  def fatal(msg: String, loc: LogLocator) = log('fatal, msg, ctx, loc)
  def info (msg: String, loc: LogLocator) = log('info,  msg, ctx, loc)
  def warn (msg: String, loc: LogLocator) = log('warn,  msg, ctx, loc)
}
