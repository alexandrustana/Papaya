import cats.effect.{ExitCode, IO, IOApp}
import com.papaya.database.model.KLineDAO
import com.papaya.historical.data.HistoricalData
import slick.lifted.TableQuery

object Papaya extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {

    for {
      _ <- HistoricalData.run
    } yield ExitCode.Success
  }

}
