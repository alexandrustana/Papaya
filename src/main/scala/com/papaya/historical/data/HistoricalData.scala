package com.papaya.historical.data

import cats.effect.IO
import com.papaya.computations.{MovingAverage, RSI}
import com.papaya.database.model.Psql
import com.papaya.settings.AppConfig
import io.github.paoloboni.binance.BinanceClient
import io.github.paoloboni.binance.common.response.CirceResponse
import io.github.paoloboni.binance.spot.parameters.v3.KLines
import io.github.paoloboni.binance.common.{Interval, KLine, SpotConfig}
import io.github.paoloboni.http.QueryParamsConverter.Ops
import sttp.client3.circe.asJson

import java.time.{Instant, LocalDateTime, ZoneId}
import scala.concurrent.Await
import scala.concurrent.duration.{Duration, DurationInt}

object HistoricalData {


  val kLineQuery = KLines(symbol = "BTCUSDT", interval = Interval.`1h`, startTime = None, endTime = None, limit = 1000)

  def run(config: AppConfig) = for {
   spotConfig <- IO(SpotConfig.Default(config.spotConfiguration.get.apiKey, config.spotConfiguration.get.apiSecret))
    _ <- BinanceClient
      .createSpotClient[IO](spotConfig)
      .use { client => {
        val uri = spotConfig.restBaseUrl.addPath("api", "v3").addPath("klines").addParams(kLineQuery.toQueryParams)
        client.client.get[CirceResponse[List[KLine]]](
          uri = uri,
          responseAs = asJson[List[KLine]],
          limiters = client.rateLimiters.requestsOnly
        )
      } map {
        case Right(value) =>
        println(s"EMA: ${MovingAverage.calculateEMA(value, 50).last}")
        println(s"RSI: ${RSI.calculateRSI(value.map(_.close.doubleValue), 14).last}")
          Await.result(Psql.insertKline(value.last, "BTCUSDT"), 10.seconds)
        case Left(e) => e
      }
      }
  } yield IO.unit
}
