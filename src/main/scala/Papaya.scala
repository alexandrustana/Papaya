import cats.effect.{ExitCode, IO, IOApp}
import com.papaya.historical.data.HistoricalData
import com.papaya.settings.Configuration

object Papaya extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {

    for {
      _ <- IO(println("Server is starting!"))
      loaded <- Configuration.loadConfiguration
      _ <- HistoricalData.run(loaded)
    } yield ExitCode.Success
  }

}
