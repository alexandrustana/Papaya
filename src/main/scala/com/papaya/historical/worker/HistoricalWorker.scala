package com.papaya.historical.worker

import cats.effect.IO
import cats.effect.unsafe.implicits.global
import com.papaya.binance.client.BClient
import com.papaya.database.Psql
import com.papaya.settings.AppConfig
import io.github.paoloboni.binance.common.{Interval, SpotConfig}

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object HistoricalWorker {

  val QUOTE_NAME = "BTCUSDT"
  val DEFAULT_INTERVAL = 1
  val DEFAULT_LIMIT_SIZE = 1000
  implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

  def startUp(bClient: BClient, config: AppConfig) = config.quotes
    .map { case (quoteName, quoteConfig) =>
      for {
        lastModel <- IO(Await
          .result(Psql.getLastKline(quoteName).map(_.headOption), Duration(10, TimeUnit.SECONDS)))
        spotConfig =
          SpotConfig
            .Default(config.spotConfiguration.get.apiKey, config.spotConfiguration.get.apiSecret)
        deltaKLines <-
          BClient
            .getHistoricData(
              bClient.spotApi,
              spotConfig,
              quoteName,
              lastModel
                .map(_.closingTime.toLocalDateTime)
                .getOrElse(
                  LocalDateTime
                    .now()
                    .minus(quoteConfig.limitSize / 24 / quoteConfig.interval, ChronoUnit.DAYS)),
              toInterval(quoteConfig.interval),
              quoteConfig.limitSize)
        r <- IO(
          Await.result(Psql.insertData(deltaKLines, quoteName), Duration(10, TimeUnit.SECONDS)))
      } yield r
    }
    .headOption
    .get

}
