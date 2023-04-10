package com.papaya.binance.client
//
import cats.effect.{IO, Resource}
import com.papaya.settings.AppConfig
import io.github.paoloboni.binance.BinanceClient
import io.github.paoloboni.binance.common.SpotConfig
import io.github.paoloboni.binance.spot.SpotApi

case class BClient(spotApi: Resource[IO, SpotApi[IO]])

object BClient {

  def apply(appConfig: AppConfig): IO[BClient] = for {
    config <- createSpotConfig(appConfig)
  } yield BClient(BinanceClient
    .createSpotClient[IO](config))

  private def createSpotConfig(appConfig: AppConfig): IO[SpotConfig] = for {
    config <-
      IO(SpotConfig.Default(apiKey = appConfig.spotConfiguration.map(_.apiKey).getOrElse("***"),
        apiSecret = appConfig.spotConfiguration.map(_.apiSecret).getOrElse("***")))
  } yield config

//  def runClient(config: SpotConfig) =   BinanceClient
//    .createSpotClient[IO](config)
}