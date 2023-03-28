package com.papaya.computations

import cats.effect.IO

import com.papaya.models.InvalidEMA50Size
import io.github.paoloboni.binance.common.KLine

object MovingAverage {

  val FINING_WEIGHT = 2 // usually it's 2, this can be configured

  private def calculateMultiplier(klines: List[KLine])  =  for {
    candles <- if(klines.size < 50) Left(InvalidEMA50Size("Size is too small to be calculated")) else Right(klines)
    multiplier <- Right(FINING_WEIGHT/(candles.size + 1))
  } yield multiplier

  private def calculateEMA(klines: List[KLine], multiplier: Double) = {

  }

  def calculateEMA50(list: List[KLine])  = for {
    multiplier <- calculateMultiplier(list)
  } yield IO.unit
}
