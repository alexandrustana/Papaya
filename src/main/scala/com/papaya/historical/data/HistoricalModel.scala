package com.papaya.historical.data

import io.github.paoloboni.binance.common.KLine

import java.time.{Instant, LocalDateTime, ZoneId}
import scala.language.implicitConversions

//candlestick
//https://www.investopedia.com/trading/candlestick-charting-what-is-it/
case class HistoricalModel(
      id: Int,
      quoteName: String,
      openTime: LocalDateTime,
      openPrice: BigDecimal,
      highPrice: BigDecimal,
      lowPrice: BigDecimal,
      closePrice: BigDecimal,
      volume: BigDecimal,
      closingTime: LocalDateTime,
      quoteAssetVolume: BigDecimal,
      numberOfTrades: Int,
      buyBaseAssetVolume: BigDecimal,
      buyQuoteAssetVolume: BigDecimal
)

