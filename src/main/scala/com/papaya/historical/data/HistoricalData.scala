package com.papaya.historical.data

import cats.effect.IO
import com.papaya.binance.client.BClient
import com.papaya.computations.{MovingAverage, RSI}
import com.papaya.database.model.Psql
import com.papaya.settings.AppConfig
import io.github.paoloboni.binance.BinanceClient
import io.github.paoloboni.binance.common.response.CirceResponse
import io.github.paoloboni.binance.spot.parameters.v3.KLines
import io.github.paoloboni.binance.common.{Interval, KLine, SpotConfig}
import io.github.paoloboni.http.QueryParamsConverter.Ops
import sttp.client3.circe.asJson

import scala.concurrent.Await
import scala.concurrent.duration.{Duration, DurationInt}

object HistoricalData {


  val kLineQuery = KLines(symbol = "BTCUSDT", interval = Interval.`1h`, startTime = None, endTime = None, limit = 1000)

  def run(bClient: BClient, config: AppConfig) = for {
    spotConfig <- IO(SpotConfig.Default(config.spotConfiguration.get.apiKey, config.spotConfiguration.get.apiSecret))
    uri = spotConfig.restBaseUrl.addPath("api", "v3").addPath("klines").addParams(kLineQuery.toQueryParams)
    kLinesResponse <- bClient.spotApi
      .use { client =>
        client.client.get[CirceResponse[List[KLine]]](
          uri = uri,
          responseAs = asJson[List[KLine]],
          limiters = client.rateLimiters.requestsOnly)
      }
  } yield {
    println(s"EMA: ${MovingAverage.calculateEMA(kLinesResponse.getOrElse(List.empty), 50).last}")
    println(s"RSI: ${RSI.calculateRSI(kLinesResponse.getOrElse(List.empty).map(_.close.doubleValue), 14).last}")
  }
}
