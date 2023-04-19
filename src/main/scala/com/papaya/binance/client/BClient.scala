package com.papaya.binance.client

import cats.effect.{IO, Resource}
import com.papaya.binance.client.BClient.Client
import com.papaya.settings.AppConfig
import io.github.paoloboni.binance.BinanceClient
import io.github.paoloboni.binance.common.response.CirceResponse
import io.github.paoloboni.binance.common.{Interval, KLine, SpotConfig}
import io.github.paoloboni.binance.spot.SpotApi
import io.github.paoloboni.binance.spot.parameters.v3.KLines
import io.github.paoloboni.http.QueryParamsConverter.Ops
import sttp.client3.circe.asJson

import java.time.{LocalDateTime, ZoneOffset}

case class BClient(spotApi: Client)

object BClient {

  type Client = Resource[IO, SpotApi[IO]]

  val defaultQuery = KLines(
    symbol = "BTCUSDT",
    interval = Interval.`1h`,
    startTime = None,
    endTime = None,
    limit = 1000)

  def apply(appConfig: AppConfig): IO[BClient] = for {
    config <- createSpotConfig(appConfig)
  } yield BClient(
    BinanceClient
      .createSpotClient[IO](config))

  private def createSpotConfig(appConfig: AppConfig): IO[SpotConfig] = for {
    config <-
      IO(
        SpotConfig.Default(
          apiKey = appConfig.spotConfiguration.map(_.apiKey).getOrElse("***"),
          apiSecret = appConfig.spotConfiguration.map(_.apiSecret).getOrElse("***")))
  } yield config

  def getHistoricData(
      api: Client,
      config: SpotConfig,
      name: String,
      endDate: LocalDateTime,
      interval: Interval = Interval.`1h`,
      limitSize: Int = 1000) = {
    val uri = config.restBaseUrl
      .addPath("api", "v3")
      .addPath("klines")
      .addParams(KLines(
        symbol = name,
        interval = interval,
        startTime = Some(endDate.toInstant(ZoneOffset.UTC)),
        endTime = Some(LocalDateTime.now().toInstant(ZoneOffset.UTC)),
        limit = limitSize).toQueryParams)
    api
      .use { client =>
        client.client.get[CirceResponse[List[KLine]]](
          uri = uri,
          responseAs = asJson[List[KLine]],
          limiters = client.rateLimiters.requestsOnly)
      }
      .map(v => v.getOrElse(List.empty))
  }

  def getGetDefaultHistoricData(
      api: Client,
      config: SpotConfig,
      name: String): IO[List[KLine]] = {
    val uri = config.restBaseUrl
      .addPath("api", "v3")
      .addPath("klines")
      .addParams(defaultQuery.copy(symbol = name).toQueryParams)
    api
      .use { client =>
        client.client.get[CirceResponse[List[KLine]]](
          uri = uri,
          responseAs = asJson[List[KLine]],
          limiters = client.rateLimiters.requestsOnly)
      }
      .map(v => v.getOrElse(List.empty))
  }

}
