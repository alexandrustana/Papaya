import cats.effect.{ExitCode, IO, IOApp}
import com.papaya.binance.client.BClient
import com.papaya.historical.worker.HistoricalData
import com.papaya.settings.Configuration

object Papaya extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {

    for {
      _ <- IO(println("Server is starting!"))
      loaded <- Configuration.loadConfiguration
      client <- BClient(loaded)
      _ <- HistoricalData.run(client, loaded)
    } yield ExitCode.Success
  }

}
