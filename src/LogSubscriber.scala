package com.bluetheta.conlog

import scala.collection.mutable.Subscriber

trait LogSubscriber extends Subscriber[LogEvent, Logger]
