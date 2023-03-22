import cats.effect.{ExitCode, IO, IOApp}
import com.papaya.historical.data.HistoricalData

object Papaya extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {
    for {
      _ <- HistoricalData.run.foreverM
    } yield ExitCode.Success
  }

}
