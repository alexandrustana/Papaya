package com.papaya.database.model

import com.papaya.historical.data.HistoricalModel
import slick.jdbc.PostgresProfile

import java.sql.Timestamp
import java.time.LocalDateTime

class SlickTablesGeneric(val profile: PostgresProfile) {

  import profile.api._

  class KLineDAO(tag: Tag) extends Table[HistoricalModel](tag, None, "historic-prices") {

    override def * = (
      quoteName,
      openTime,
      openPrice,
      highPrice,
      lowPrice,
      closePrice,
      volume,
      closingTime,
      quoteAssetVolume,
      numberOfTrades,
      buyBaseAssetVolume,
      buyQuoteAssetVolume) <> (HistoricalModel.tupled, HistoricalModel.unapply)

    def quoteName = column[String]("quote_name")

    def openTime = column[Timestamp]("open_time")

    def openPrice = column[BigDecimal]("open_price")

    def highPrice = column[BigDecimal]("high_price")

    def lowPrice = column[BigDecimal]("low_price")

    def closePrice = column[BigDecimal]("close_price")

    def volume = column[BigDecimal]("volume")

    def closingTime = column[Timestamp]("closing_time")

    def quoteAssetVolume = column[BigDecimal]("quote_asset_volume")

    def numberOfTrades = column[Int]("number_of_trades")

    def buyBaseAssetVolume = column[BigDecimal]("buy_base_asset_volume")

    def buyQuoteAssetVolume = column[BigDecimal]("buy_quote_asset_volume")

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  }

}
