package com.papaya.database.model

import com.papaya.historical.data.HistoricalModel
import com.papaya.settings.Configuration
import io.github.paoloboni.binance.common.KLine

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
      openTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(kline.openTime),  ZoneId.systemDefault()),
      openPrice = kline.open,
      highPrice = kline.high,
      lowPrice = kline.low,
      volume = kline.volume,
      closingTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(kline.closeTime),  ZoneId.systemDefault()),
      quoteAssetVolume = kline.quoteAssetVolume,
      numberOfTrades = kline.numberOfTrades,
      buyBaseAssetVolume = kline.takerBuyBaseAssetVolume,
      buyQuoteAssetVolume = kline.takerBuyQuoteAssetVolume,
    )
  }
}
