package com.papaya.database.model

import com.papaya.historical.data.HistoricalModel
import slick.jdbc.PostgresProfile

import java.time.LocalDateTime

class SlickTablesGeneric(val profile: PostgresProfile) {

  import profile.api._

  class KLineDAO(tag: Tag) extends Table[HistoricalModel](tag, None, "historic-prices") {
    override def * = (id, quoteName, openTime, openPrice, highPrice, lowPrice, volume, closingTime, quoteAssetVolume, numberOfTrades, buyBaseAssetVolume, buyQuoteAssetVolume) <> (HistoricalModel.tupled, HistoricalModel.unapply)

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def quoteName = column[String]("quote_name")

    def openTime = column[LocalDateTime]("open_time")

    def openPrice = column[BigDecimal]("open_price")

    def highPrice = column[BigDecimal]("high_price")

    def lowPrice = column[BigDecimal]("low_price")

    def volume = column[BigDecimal]("volume")

    def closingTime = column[LocalDateTime]("closing_time")

    def quoteAssetVolume = column[BigDecimal]("quote_asset_volume")

    def numberOfTrades = column[Int]("number_of_trades")

    def buyBaseAssetVolume = column[BigDecimal]("buy_base_asset_volume")

    def buyQuoteAssetVolume = column[BigDecimal]("buy_quote_asset_volume")
  }
}