package com.papaya.historical.data

import io.github.paoloboni.binance.common.KLine

import java.time.{Instant, LocalDateTime, ZoneId}
import scala.language.implicitConversions

//candlestick
//https://www.investopedia.com/trading/candlestick-charting-what-is-it/
case class HistoricalModel(
                          openTime: LocalDateTime,
                          openPrice: BigDecimal,
                          highPrice: BigDecimal,
                          lowPrice: BigDecimal,
                          volume: BigDecimal,
                          closingTime: LocalDateTime,
                          quoteAssetVolume: BigDecimal,
                          numberOfTrades: Int,
                          buyBaseAssetVolume: BigDecimal,
                          buyQuoteAssetVolume: BigDecimal
                          )
object HistoricalModel
{
  def toHistoricalModel(kline: KLine) = {
    HistoricalModel(openTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(kline.openTime),  ZoneId.systemDefault()),
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