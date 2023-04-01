package com.papaya.computations

object RSI {

  def calculateRSI(data: List[Double], period: Int): List[Double] = {
    val changes = data.zip(data.tail).map { case (prev, curr) => curr - prev }
    val ups = changes.filter(_ > 0)
    val downs = changes.filter(_ < 0).map(Math.abs)
    var upAvg = ups.take(period).sum / period
    var downAvg = downs.take(period).sum / period
    var rs = upAvg / downAvg
    var rsiList = List(100.0 - 100.0 / (1 + rs))
    for (i <- period until data.length) {
      val change = data(i) - data(i - 1)
      val up = if (change > 0) change else 0
      val down = if (change < 0) Math.abs(change) else 0
      upAvg = (upAvg * (period - 1) + up) / period
      downAvg = (downAvg * (period - 1) + down) / period
      rs = upAvg / downAvg
      rsiList = rsiList :+ 100.0 - 100.0 / (1 + rs)
    }
    rsiList
  }

}
