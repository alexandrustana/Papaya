import cats.effect.{ExitCode, IO, IOApp}
import com.papaya.binance.client.BClient
import com.papaya.historical.worker.HistoricalWorker
import com.papaya.settings.Configuration

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Papaya extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {

    for {
      _ <- IO(println("Server is starting!"))
      loaded <- Configuration.loadConfiguration
      client <- BClient(loaded)
      _ <- HistoricalWorker.run(client, loaded)
    } yield {
      ExitCode.Success
    }
  }

}
