package com.bluetheta.conlog

trait LogLocator { def location: String }

object Nowhere extends LogLocator { def location = "" }
