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
  implicit val ec: scala.concurrent.ExecutionContext = scala.concurrent.ExecutionContext.global

  def run(bClient: BClient, config: AppConfig) = for {
    lastModel <- IO(
      Await
        .result(Psql.getLastKline(QUOTE_NAME).map(_.headOption), Duration(10, TimeUnit.SECONDS)))
    spotConfig =
      SpotConfig
        .Default(config.spotConfiguration.get.apiKey, config.spotConfiguration.get.apiSecret)
    deltaKLines <-
      BClient
        .getHistoricData(
          bClient.spotApi,
          spotConfig,
          lastModel.map(_.quoteName).getOrElse(QUOTE_NAME),
          lastModel
            .map(_.closingTime.toLocalDateTime)
            .getOrElse(LocalDateTime.now().minus(1000 / 24 / 1, ChronoUnit.DAYS)))
    r <- IO(
      Await.result(Psql.insertData(deltaKLines, QUOTE_NAME), Duration(10, TimeUnit.SECONDS)))
  } yield r

}
