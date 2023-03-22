import cats.effect.{ExitCode, IO, IOApp}

object Papaya extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {
    for {
      _ <- PriceMonitor.run.foreverM
    } yield ExitCode.Success
  }

}
