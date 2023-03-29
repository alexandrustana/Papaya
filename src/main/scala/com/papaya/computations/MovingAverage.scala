package com.papaya.computations

import com.papaya.models.InvalidEMA50Size
import io.github.paoloboni.binance.common.KLine

object MovingAverage {

  val FINING_WEIGHT = 2 // usually it's 2, this can be configured

  private def calculateMultiplier(klines: List[KLine])  =  for {
//    candles <- if(klines.size < 50) Left(InvalidEMA50Size(s"Size is too small to be calculated ${klines.size}")) else Right(klines)
    multiplier <- Right(FINING_WEIGHT/(klines.size + 1))
  } yield multiplier

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

//  def calculateEMA50(candles: List[KLine]) = calculateEMA(50, candles)
//  def calculateEMA200(candles: List[KLine]) = calculateEMA(200, candles)
}
