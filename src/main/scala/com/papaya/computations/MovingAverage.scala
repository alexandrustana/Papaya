package com.papaya.computations

import io.github.paoloboni.binance.common.KLine

object MovingAverage {

  def calculateSimpleMovingAverage(range: Int, candles: List[KLine]) = {
     candles.map(_.close).sum/range
  }

  def calculateEMA(data: List[KLine], period: Int): List[BigDecimal] = {
    val multiplier = BigDecimal(2.0 / (period + 1.0))
    var ema = data.map(_.close).take(period).sum / period
    var emaList = List(ema)
    for (i <- period until data.length) {
      ema = (data.map(_.close)(i) - ema) * multiplier + ema
      emaList = emaList :+ ema
    }
    emaList
  }

}
