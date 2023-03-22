//package com.papaya.binance.client
//
//import cats.effect.IO
//import com.papaya.settings.AppConfig
//import io.github.paoloboni.binance.BinanceClient
//import io.github.paoloboni.binance.common.SpotConfig
//import io.github.paoloboni.binance.spot.SpotApi
//
//case class BClient(spotApi: SpotApi[IO])
//
//object BClient {
//
//  private def createSpotConfig(appConfig: AppConfig): Either[Exception, SpotConfig] = for {
//    config <- if (appConfig.spotConfiguration.apiKey == "***" || appConfig.spotConfiguration.apiSecret == "***")
//      Left(new Exception("Invalid configuration")) else
//      Right(SpotConfig.Default(apiKey = appConfig.spotConfiguration.apiKey, apiSecret = appConfig.spotConfiguration.apiSecret))
//  } yield config
//
//  def apply(appConfig: AppConfig): Either[Exception, BClient] = for {
//    config <- createSpotConfig(appConfig)
//    binanceClient <-  BinanceClient
//      .createSpotClient[IO](config).map(client => BClient(client.client))
//  } yield BClient(binanceClient)
//
//}