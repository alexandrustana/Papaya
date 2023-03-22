package com.papaya.historical.data

import cats.effect.IO
import cats.effect.std.Console
import fs2.Stream
import io.github.paoloboni.binance.BinanceClient
import io.github.paoloboni.binance.spot.parameters.v3.KLines
import io.github.paoloboni.binance.common.{Interval, KLine, SpotConfig}

import scala.concurrent.duration.DurationInt

object HistoricalData {


  val config = SpotConfig.Default(
    apiKey = "***",
    apiSecret = "***"
  )

  val kLineQuery = KLines(symbol = "BTCUSDT", interval = Interval.`4h`, startTime = None, endTime = None, limit = 500)
  
  def run =
    BinanceClient
      .createSpotClient[IO](config)
      .use { client =>
       client.V3.getKLines(kLineQuery)
         .evalMap(kline => Console[IO].print(s"This is it!: + ${kline}"))
         .compile
         .drain
      }

}
