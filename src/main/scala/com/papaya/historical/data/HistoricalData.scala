package com.papaya.historical.data

import cats.effect.IO
import com.papaya.computations.{MovingAverage, RSI}
import com.papaya.database.model.KLineDAO
import com.papaya.historical.data.HistoricalModel.toHistoricalModel
import com.papaya.settings.Configuration
import io.github.paoloboni.binance.BinanceClient
import io.github.paoloboni.binance.common.response.CirceResponse
import io.github.paoloboni.binance.spot.parameters.v3.KLines
import io.github.paoloboni.binance.common.{Interval, KLine, SpotConfig}
import io.github.paoloboni.http.QueryParamsConverter.Ops
import slick.dbio.DBIO
import slick.lifted.TableQuery
import sttp.client3.circe.asJson

import java.time.{Instant, LocalDateTime, ZoneId}

object HistoricalData {


  val config = SpotConfig.Default(
    apiKey = "***",
    apiSecret = "***"
  )

  val kLineQuery = KLines(symbol = "BTCUSDT", interval = Interval.`1h`, startTime = None, endTime = None, limit = 1000)

  def run =
    BinanceClient
      .createSpotClient[IO](config)
      .use { client => {
        val uri = config.restBaseUrl.addPath("api", "v3").addPath("klines").addParams(kLineQuery.toQueryParams)
        client.client.get[CirceResponse[List[KLine]]](
          uri = uri,
          responseAs = asJson[List[KLine]],
          limiters = client.rateLimiters.requestsOnly
        )
      } map {
        case Right(value) => value.foreach(kline => {
          println("-------------------- KLine ------------------------")
          println(s"OpenTime: ${LocalDateTime.ofInstant(Instant.ofEpochMilli(kline.openTime), ZoneId.systemDefault())}")
          println(s"ClosingTime: ${LocalDateTime.ofInstant(Instant.ofEpochMilli(kline.closeTime), ZoneId.systemDefault())}")
          println(s"Open price: ${kline.open}")
          println(s"High price: ${kline.high}")
          println(s"Low price: ${kline.low}")
          println(s"Volume: ${kline.volume}")
          println(s"Volume: ${kline}")
          println("---------------------------------------------------")
        })
        println(s"EMA: ${MovingAverage.calculateEMA(value, 50)}")
        println(s"RSI: ${RSI.calculateRSI(value.map(_.close.doubleValue), 14)}")
          var klineTable = TableQuery[KLineDAO]
          val insertPlayerQuery = klineTable += toHistoricalModel(value.last).toString
          DBIO.seq(insertPlayerQuery)
          Configuration.getDbConfiguration.run(insertPlayerQuery)
        case Left(e) => e
      }
//       client.V3.getKLines(kLineQuery)
//         .evalMap(kline => Console[IO].print(s"This is it!: + ${kline}"))
//         .compile
//         .drain
      }
}
