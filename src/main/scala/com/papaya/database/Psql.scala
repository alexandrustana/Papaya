package com.papaya.database

import cats.data.{EitherT, OptionT}
import com.papaya.database.model.SlickTables
import com.papaya.historical.data.HistoricalModel
import com.papaya.settings.Configuration
import io.github.paoloboni.binance.common.KLine
import slick.jdbc.GetResult

import java.time.{Instant, LocalDateTime, ZoneId}
import scala.concurrent.Future

object Psql {
  import SlickTables.profile.api._

  def insertKline(kLine: KLine, name: String): Future[Int] = {
    val insertQuery = SlickTables.historicTable += toHistoricalModel(kLine, name)
    Configuration.db.run(insertQuery)
  }

  def toHistoricalModel(kline: KLine, name: String) = {
    HistoricalModel(
      id = 0,
      quoteName = name,
      openTime =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(kline.openTime), ZoneId.systemDefault()),
      openPrice = kline.open,
      highPrice = kline.high,
      lowPrice = kline.low,
      closePrice = kline.close,
      volume = kline.volume,
      closingTime =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(kline.closeTime), ZoneId.systemDefault()),
      quoteAssetVolume = kline.quoteAssetVolume,
      numberOfTrades = kline.numberOfTrades,
      buyBaseAssetVolume = kline.takerBuyBaseAssetVolume,
      buyQuoteAssetVolume = kline.takerBuyQuoteAssetVolume)
  }

  def getLastKline(name: String): Future[Seq[HistoricalModel]] = {
    implicit val getResultMovie: GetResult[HistoricalModel] = GetResult(r =>
      HistoricalModel(
        r.<<,
        r.<<,
        LocalDateTime.parse(r.nextString()),
        r.<<,
        r.<<,
        r.<<,
        r.<<,
        r.<<,
        LocalDateTime.parse(r.nextString()),
        r.<<,
        r.<<,
        r.<<,
        r.<<))
    val historicQuery =
      sql"""SELECT * FROM public.historic-prices where quote_name = $name ORDER BY closing_time DESC LIMIT 1"""
        .as[HistoricalModel]
    Configuration.db.run(historicQuery)
  }

}
