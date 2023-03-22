import cats.effect.std.Console
import cats.effect.{IO, IOApp}
import fs2.Stream
import io.github.paoloboni.binance.BinanceClient
import io.github.paoloboni.binance.common.SpotConfig

import scala.concurrent.duration.DurationInt

object PriceMonitor extends IOApp.Simple {

  val config = SpotConfig.Default(
    apiKey = "***",
    apiSecret = "***"
  )

  override def run: IO[Unit] =
    BinanceClient
      .createSpotClient[IO](config)
      .use { client =>
        Stream
          .awakeEvery[IO](5.seconds)
          .repeat
          .evalMap(_ => client.V3.getPrices())
          .evalMap(prices => Console[IO].println("Current prices: " + prices))
          .compile
          .drain
      }
}
