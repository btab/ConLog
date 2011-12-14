package com.bluetheta.conlog

trait LogSubscriber { def notify(log: Logger, event: LogEvent) }
