package com.papaya.historical.data

import java.sql.Timestamp
import java.time.{Instant, LocalDateTime, ZoneId}
import scala.language.implicitConversions

//candlestick
//https://www.investopedia.com/trading/candlestick-charting-what-is-it/
case class HistoricalModel(
    id: Int,
    quoteName: String,
    openTime: Timestamp,
    openPrice: BigDecimal,
    highPrice: BigDecimal,
    lowPrice: BigDecimal,
    closePrice: BigDecimal,
    volume: BigDecimal,
    closingTime: Timestamp,
    quoteAssetVolume: BigDecimal,
    numberOfTrades: Int,
    buyBaseAssetVolume: BigDecimal,
    buyQuoteAssetVolume: BigDecimal)
