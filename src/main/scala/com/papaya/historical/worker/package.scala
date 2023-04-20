package com.papaya.historical

import io.github.paoloboni.binance.common.Interval

import scala.concurrent.duration.Duration
import scala.language.implicitConversions

package object worker {

  def toInterval(duration: Int): Interval = duration match {
    case 1 => Interval.`1h`
    case 2 => Interval.`2h`
    case 4 => Interval.`4h`
    case 6 => Interval.`6h`
    case 8 => Interval.`8h`
    case 12 => Interval.`12h`
    case 24 => Interval.`1d`
    case 48 => Interval.`3d`
    case 168 => Interval.`1w`
  }

}
